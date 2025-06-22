package com.back2basics.comment.service;

import com.back2basics.comment.model.Comment;
import com.back2basics.comment.port.in.CommentCreateUseCase;
import com.back2basics.comment.port.in.command.CommentCreateCommand;
import com.back2basics.comment.port.out.CommentCreatePort;
import com.back2basics.comment.service.notification.CommentNotificationSender;
import com.back2basics.infra.validation.validator.CommentValidator;
import com.back2basics.infra.validation.validator.PostValidator;
import com.back2basics.mention.MentionNotificationSender;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentCreateService implements CommentCreateUseCase {

    private final CommentCreatePort commentCreatePort;
    private final PostValidator postValidator;
    private final CommentValidator commentValidator;
    private final CommentNotificationSender commentNotificationSender;
    private final MentionNotificationSender mentionNotificationSender;

    @Override
    public Long createComment(Long userId, String userIp, CommentCreateCommand command) {
        postValidator.findPost(command.getPostId());
        commentValidator.validateParentComment(command.getParentId(), command.getPostId());

        Comment comment = Comment.create(command, userIp, userId);

        Long commentId = commentCreatePort.save(comment);
        commentNotificationSender.sendNotification(userId, commentId, command);

        mentionNotificationSender.notifyMentionedUsers(
            userId,
            commentId,
            command.getContent()
        );

        return commentId;
    }
}
