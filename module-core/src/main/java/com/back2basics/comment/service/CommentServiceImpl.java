package com.back2basics.comment.service;

import com.back2basics.comment.model.Comment;
import com.back2basics.comment.port.in.CreateCommentUseCase;
import com.back2basics.comment.port.in.DeleteCommentUseCase;
import com.back2basics.comment.port.in.UpdateCommentUseCase;
import com.back2basics.comment.port.out.CommentRepositoryPort;
import com.back2basics.infra.validation.validator.CommentValidator;
import com.back2basics.service.comment.dto.CommentCreateCommand;
import com.back2basics.service.comment.dto.CommentUpdateCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements
    CreateCommentUseCase,
    UpdateCommentUseCase,
    DeleteCommentUseCase {

    private final CommentRepositoryPort commentRepositoryPort;
    private final CommentValidator commentValidator;
    private final CommentModelRelationHelper commentModelRelationHelper;


    @Override
    public Long createComment(CommentCreateCommand command) {
        Comment comment = Comment.builder()
            .postId(command.getPostId())
            .authorName(command.getAuthorName())
            .content(command.getContent())
            .parentCommentId(command.getParentId())
            .build();

        commentModelRelationHelper.assignRelations(command, comment);

        return commentRepositoryPort.save(comment);
    }

    @Override
    public void updateComment(Long id, CommentUpdateCommand command) {
        Comment comment = commentValidator.findComment(id);
        commentValidator.isAuthor(comment, command.getRequesterName());

        comment.update(command);
        commentRepositoryPort.update(comment);
    }

    @Override
    public void deleteComment(Long id, String requesterName) {
        Comment comment = commentValidator.findComment(id);
        commentValidator.isAuthor(comment, requesterName);

        commentRepositoryPort.delete(comment);
    }
}
