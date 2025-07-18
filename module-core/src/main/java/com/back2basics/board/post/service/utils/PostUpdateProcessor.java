package com.back2basics.board.post.service.utils;

import com.back2basics.board.link.service.LinkUpdateService;
import com.back2basics.board.post.model.Post;
import com.back2basics.board.post.port.in.command.PostUpdateCommand;
import com.back2basics.board.post.port.out.PostUpdatePort;
import com.back2basics.history.model.DomainType;
import com.back2basics.history.service.HistoryLogService;
import com.back2basics.infra.s3.dto.PresignedUploadCompleteInfo;
import com.back2basics.infra.validator.PostValidator;
import com.back2basics.mention.MentionNotificationSender;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
@RequiredArgsConstructor
public class PostUpdateProcessor {

    private final PostValidator postValidator;
    private final PostUpdatePort postUpdatePort;
    private final LinkUpdateService linkUpdateService;
    private final MentionNotificationSender mentionNotificationSender;
    private final HistoryLogService historyLogService;
    private final PostFileService postFileService;

    public void updateWithMultipart(Long userId, String userIp, Long postId,
        PostUpdateCommand command, List<MultipartFile> files) throws IOException {

        Post post = postValidator.findPost(postId);
        Post before = Post.copyOf(post);

        post.update(command, userIp);
        Post updated = postUpdatePort.update(post);

        linkUpdateService.updateLinks(command.getNewLinks(), command.getLinkIdsToDelete(), postId);
        postFileService.replaceFiles(files, command.getFileIdsToDelete(), postId);

        mentionNotificationSender.notifyMentionedUsers(userId, post.getProjectId(), postId, post.getContent());
        historyLogService.logUpdated(DomainType.POST, userId, before, updated, "게시글 정보 수정");
    }

    public void updateWithPresigned(Long userId, String userIp, Long postId,
        PostUpdateCommand command, List<PresignedUploadCompleteInfo> uploadedFiles) {

        Post post = postValidator.findPost(postId);
        Post before = Post.copyOf(post);

        post.update(command, userIp);
        Post updated = postUpdatePort.update(post);

        linkUpdateService.updateLinks(command.getNewLinks(), command.getLinkIdsToDelete(), postId);
        postFileService.replacePresignedFiles(uploadedFiles, command.getFileIdsToDelete(), postId);

        mentionNotificationSender.notifyMentionedUsers(userId, post.getProjectId(), postId, post.getContent());
        historyLogService.logUpdated(DomainType.POST, userId, before, updated, "게시글 정보 수정");
    }
}