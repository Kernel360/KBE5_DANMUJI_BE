package com.back2basics.comment.service;

import com.back2basics.board.post.model.Post;
import com.back2basics.comment.model.Comment;
import com.back2basics.comment.port.in.CommentUpdateUseCase;
import com.back2basics.comment.port.in.command.CommentUpdateCommand;
import com.back2basics.comment.port.out.CommentUpdatePort;
import com.back2basics.infra.validator.CommentValidator;
import com.back2basics.infra.validator.PostValidator;
import com.back2basics.mention.MentionNotificationSender;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentUpdateService implements CommentUpdateUseCase {

    private final PostValidator postValidator;
    private final CommentValidator commentValidator;
    private final CommentUpdatePort commentUpdatePort;
    private final MentionNotificationSender mentionNotificationSender;


    @Override
    public void updateComment(Long userId, String userIp, Long commentId,
        CommentUpdateCommand command) {
        Comment comment = commentValidator.findComment(commentId);
        Post post = postValidator.findPost(comment.getPostId());
        commentValidator.isAuthor(comment, userId);

        comment.update(command, userIp);
        commentUpdatePort.update(comment);
        mentionNotificationSender.notifyMentionedUsers(userId, post.getProjectId(), post.getId(),
            command.getContent());
    }
}
