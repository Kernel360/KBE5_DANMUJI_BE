package com.back2basics.comment.service;

import com.back2basics.comment.model.Comment;
import com.back2basics.comment.port.in.CommentCreateUseCase;
import com.back2basics.comment.port.in.command.CommentCreateCommand;
import com.back2basics.comment.port.out.CommentCreatePort;
import com.back2basics.comment.service.notification.CommentNotificationSender;
import com.back2basics.infra.validation.validator.CommentValidator;
import com.back2basics.infra.validation.validator.PostValidator;
import com.back2basics.user.model.User;
import com.back2basics.user.port.out.UserQueryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentCreateService implements CommentCreateUseCase {

    private final CommentCreatePort commentCreatePort;
    private final PostValidator postValidator;
    private final CommentValidator commentValidator;
    private final UserQueryPort userQueryPort;
    private final CommentNotificationSender commentNotificationSender;

    @Override
    public Long createComment(Long userId, String userIp, CommentCreateCommand command) {
        postValidator.findPost(command.getPostId());

        if (command.getParentId() != null) {
            commentValidator.findComment(command.getParentId());
            commentValidator.validateParentPost(command.getParentId(),
                command.getPostId());
        }

        User user = userQueryPort.findById(userId);

        Comment comment = Comment.builder()
            .postId(command.getPostId())
            .authorIp(userIp)
            .author(user)
            .content(command.getContent())
            .parentCommentId(command.getParentId())
            .build();

        Long commentId = commentCreatePort.save(comment);
        commentNotificationSender.sendNotification(userId, commentId, command);
        
        return commentId;
    }
}
