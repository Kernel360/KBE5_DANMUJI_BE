package com.back2basics.comment.service.notification;

import com.back2basics.board.post.model.Post;
import com.back2basics.comment.model.Comment;
import com.back2basics.comment.port.in.command.CommentCreateCommand;
import com.back2basics.infra.validation.validator.CommentValidator;
import com.back2basics.infra.validation.validator.PostValidator;
import com.back2basics.notify.model.NotificationType;
import com.back2basics.notify.port.in.NotifyUseCase;
import com.back2basics.notify.port.in.command.SendNotificationCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommentNotificationSender {

    private final NotifyUseCase notifyUseCase;
    private final PostValidator postValidator;
    private final CommentValidator commentValidator;

    public void sendNotification(Long senderId, Long commentId, CommentCreateCommand command) {
        if (command.getParentId() != null) {

            // 대댓글 -> 부모 댓글 작성자에게
            Comment parent = commentValidator.findComment(command.getParentId());
            Long receiverId = parent.getAuthorId();
            Post post = postValidator.findPost(parent.getPostId());
            if (!receiverId.equals(senderId)) {
                notifyUseCase.notify(new SendNotificationCommand(
                    receiverId,
                    post.getProjectId(),
                    post.getId(),
                    NotificationType.COMMENT_REPLY_CREATED.getDescription(),
                    NotificationType.COMMENT_REPLY_CREATED
                ));
            }
        } else {
            // 그냥 댓글 -> 게시글 작성자에게
            Post post = postValidator.findPost(command.getPostId());
            Long receiverId = post.getAuthorId();
            if (!receiverId.equals(senderId)) {
                notifyUseCase.notify(new SendNotificationCommand(
                    receiverId,
                    post.getProjectId(),
                    post.getId(),
                    NotificationType.COMMENT_POST_CREATED.getDescription(),
                    NotificationType.COMMENT_POST_CREATED
                ));
            }
        }
    }
}
