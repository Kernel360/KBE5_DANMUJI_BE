package com.back2basics.comment.service;

import com.back2basics.comment.model.Comment;
import com.back2basics.comment.port.in.CommentCreateUseCase;
import com.back2basics.comment.port.in.command.CommentCreateCommand;
import com.back2basics.comment.port.out.CommentCreatePort;
import com.back2basics.infra.validation.validator.CommentValidator;
import com.back2basics.infra.validation.validator.PostValidator;
import com.back2basics.post.model.Post;
import com.back2basics.user.model.User;
import com.back2basics.user.port.out.UserRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentCreateService implements CommentCreateUseCase {

    private final CommentCreatePort commentCreatePort;
    private final PostValidator postValidator;
    private final CommentValidator commentValidator;
    private final UserRepositoryPort userRepositoryPort;

    @Override
    public Long createComment(CommentCreateCommand command) {
        User user = userRepositoryPort.findById(command.getAuthorId());
        Comment comment = Comment.builder()
            .postId(command.getPostId())
            .author(user)
            .content(command.getContent())
            .parentCommentId(command.getParentId())
            .build();

        assignRelations(command, comment);

        return commentCreatePort.save(comment);
    }

    private void assignRelations(CommentCreateCommand command, Comment comment) {
        Post post = postValidator.findPost(command.getPostId());
        comment.assignPostId(post);

        if (command.getParentId() != null) {
            Comment parentComment = commentValidator.findComment(command.getParentId());
            parentComment.addChild(comment);
        } else {
            post.addComment(comment);
        }
    }
}
