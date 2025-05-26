package com.back2basics.service.comment;

import com.back2basics.infra.comment.validation.CommentValidator;
import com.back2basics.infra.post.validation.PostValidator;
import com.back2basics.model.comment.Comment;
import com.back2basics.model.post.Post;
import com.back2basics.service.comment.dto.CommentCreateCommand;
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