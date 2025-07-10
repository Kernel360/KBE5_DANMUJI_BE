package com.back2basics.comment.service.utils;

import com.back2basics.board.post.model.Post;
import com.back2basics.comment.model.Comment;
import com.back2basics.comment.port.in.command.CommentUpdateCommand;
import com.back2basics.comment.port.out.CommentUpdatePort;
import com.back2basics.infra.validator.CommentValidator;
import com.back2basics.infra.validator.PostValidator;
import com.back2basics.mention.MentionNotificationSender;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommentUpdateProcessor {

    private final CommentValidator commentValidator;
    private final PostValidator postValidator;
    private final CommentUpdatePort commentUpdatePort;
    private final MentionNotificationSender mentionNotificationSender;

    public void update(Long userId, String userIp, Long commentId, CommentUpdateCommand command) {
        Comment comment = validate(userId, commentId);
        Post post = postValidator.findPost(comment.getPostId());

        comment.update(command, userIp);
        commentUpdatePort.update(comment);

        handleAfterUpdate(userId, post, command);
    }

    private Comment validate(Long userId, Long commentId) {
        Comment comment = commentValidator.findComment(commentId);
        commentValidator.isAuthor(comment, userId);
        return comment;
    }

    private void handleAfterUpdate(Long userId, Post post, CommentUpdateCommand command) {
        mentionNotificationSender.notifyMentionedUsers(userId, post.getProjectId(), post.getId(), command.getContent()
        );
    }
}