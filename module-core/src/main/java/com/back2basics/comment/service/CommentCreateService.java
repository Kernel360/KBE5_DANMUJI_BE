package com.back2basics.comment.service;

import com.back2basics.comment.model.Comment;
import com.back2basics.comment.port.in.CommentCreateUseCase;
import com.back2basics.comment.port.in.command.CommentCreateCommand;
import com.back2basics.comment.port.out.CommentCreatePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentCreateService implements CommentCreateUseCase {

    private final CommentCreatePort commentCreatePort;
    private final CommentModelRelationHelper commentModelRelationHelper;

    @Override
    public Long createComment(CommentCreateCommand command) {
        Comment comment = Comment.builder()
            .postId(command.getPostId())
            .authorId(command.getAuthorId())
            .content(command.getContent())
            .parentCommentId(command.getParentId())
            .build();

        commentModelRelationHelper.assignRelations(command, comment);

        return commentCreatePort.save(comment);
    }
}
