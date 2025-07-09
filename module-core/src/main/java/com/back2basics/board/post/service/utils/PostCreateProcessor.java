package com.back2basics.board.post.service.utils;

import com.back2basics.board.link.service.LinkCreateService;
import com.back2basics.board.post.model.Post;
import com.back2basics.board.post.port.in.command.PostCreateCommand;
import com.back2basics.board.post.port.out.PostCreatePort;
import com.back2basics.board.post.service.notification.PostNotificationSender;
import com.back2basics.board.post.service.result.PostCreateResult;
import com.back2basics.history.model.DomainType;
import com.back2basics.history.service.HistoryLogService;
import com.back2basics.infra.s3.dto.PresignedUploadCompleteInfo;
import com.back2basics.infra.validator.PostValidator;
import com.back2basics.infra.validator.ProjectValidator;
import com.back2basics.infra.validator.UserValidator;
import com.back2basics.mention.MentionNotificationSender;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;


@Component
@RequiredArgsConstructor
public class PostCreateProcessor {

    private final PostValidator postValidator;
    private final ProjectValidator projectValidator;
    private final UserValidator userValidator;
    private final PostCreatePort postCreatePort;
    private final PostFileService postFileService;
    private final LinkCreateService linkCreateService;
    private final PostNotificationSender postNotificationSender;
    private final MentionNotificationSender mentionNotificationSender;
    private final HistoryLogService historyLogService;

    public PostCreateResult createWithMultipart(Long userId, Long projectId, Long stepId,
        String userIp, PostCreateCommand command,
        List<MultipartFile> files) throws IOException {

        validate(userId, projectId, command);
        Post post = Post.createFromCommand(command, userId, userIp);
        Post savedPost = postCreatePort.save(post);

        postFileService.uploadAndSave(files, savedPost.getId());
        handleAfterSave(userId, projectId, savedPost, command);

        return PostCreateResult.toResult(savedPost);
    }

    public PostCreateResult createWithPresigned(Long userId, Long projectId, Long stepId,
        String userIp, PostCreateCommand command,
        List<PresignedUploadCompleteInfo> uploadedFiles) {

        validate(userId, projectId, command);
        Post post = Post.createFromCommand(command, userId, userIp);
        Post savedPost = postCreatePort.save(post);

        postFileService.savePresigned(uploadedFiles, savedPost.getId());
        handleAfterSave(userId, projectId, savedPost, command);

        return PostCreateResult.toResult(savedPost);
    }

    private void validate(Long userId, Long projectId, PostCreateCommand command) {
        userValidator.validateNotFoundUserId(userId);
        projectValidator.findById(projectId);
        postValidator.findParentPost(command.getParentId());
    }

    private void handleAfterSave(Long userId, Long projectId, Post post, PostCreateCommand command) {
        linkCreateService.createLinks(command.getNewLinks(), post.getId());
        postNotificationSender.sendNotification(userId, post.getId(), command);
        mentionNotificationSender.notifyMentionedUsers(userId, projectId, post.getId(), post.getContent());
        historyLogService.logCreated(DomainType.POST, userId, post, "게시글 생성");
    }
}