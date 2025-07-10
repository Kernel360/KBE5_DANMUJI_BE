package com.back2basics.comment.service.utils;

import com.back2basics.board.post.model.Post;
import com.back2basics.comment.model.Comment;
import com.back2basics.comment.port.in.command.CommentCreateCommand;
import com.back2basics.comment.port.out.CommentCreatePort;
import com.back2basics.comment.service.notification.CommentNotificationSender;
import com.back2basics.infra.validator.CommentValidator;
import com.back2basics.infra.validator.PostValidator;
import com.back2basics.mention.MentionNotificationSender;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommentCreateProcessor {

    private final CommentCreatePort commentCreatePort;
    private final PostValidator postValidator;
    private final CommentValidator commentValidator;
    private final CommentNotificationSender commentNotificationSender;
    private final MentionNotificationSender mentionNotificationSender;

    public Long create(Long userId, String userIp, CommentCreateCommand command) {
        Post post = validate(userId, command);

        Comment comment = Comment.create(command, userIp, userId);
        Long commentId = commentCreatePort.save(comment);

        handleAfterSave(userId, post, command);
        return commentId;
    }

    private Post validate(Long userId, CommentCreateCommand command) {
        Post post = postValidator.findPost(command.getPostId());
        commentValidator.validateParentComment(command.getParentId(), command.getPostId());
        return post;
    }

    private void handleAfterSave(Long userId, Post post, CommentCreateCommand command) {
        commentNotificationSender.sendNotification(userId, post.getId(), command);
        mentionNotificationSender.notifyMentionedUsers(userId, post.getProjectId(), post.getId(), command.getContent()
        );
    }
}