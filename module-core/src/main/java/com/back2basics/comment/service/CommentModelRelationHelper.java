package com.back2basics.comment.service;

import com.back2basics.comment.model.Comment;
import com.back2basics.comment.port.command.CommentCreateCommand;
import com.back2basics.infra.validation.validator.CommentValidator;
import com.back2basics.infra.validation.validator.PostValidator;
import com.back2basics.post.model.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentModelRelationHelper {

    private final PostValidator postValidator;
    private final CommentValidator commentValidator;

    public void assignRelations(CommentCreateCommand command, Comment comment) {
        Post post = postValidator.findPost(command.getPostId());
        comment.assignPost(post);

        if (command.getParentId() != null) {
            Comment parentComment = commentValidator.findComment(command.getParentId());
            parentComment.addChild(comment);
        } else {
            post.addComment(comment);
        }
    }
}