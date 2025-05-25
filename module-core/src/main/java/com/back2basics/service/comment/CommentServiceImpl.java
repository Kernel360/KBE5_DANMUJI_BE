package com.back2basics.service.comment;

import com.back2basics.infra.comment.validation.CommentValidator;
import com.back2basics.model.comment.Comment;
import com.back2basics.port.in.comment.CreateCommentUseCase;
import com.back2basics.port.in.comment.DeleteCommentUseCase;
import com.back2basics.port.in.comment.UpdateCommentUseCase;
import com.back2basics.port.out.comment.CommentRepositoryPort;
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


    @Override
    public Long createComment(CommentCreateCommand command) {
        Comment comment = Comment.builder()
            .postId(command.getPostId())
            .authorName(command.getAuthorName())
            .content(command.getContent())
            .parentId(command.getParentId())
            .build();

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
        commentValidator.isAuthor(comment, command.getRequesterName());

        commentRepositoryPort.delete(comment);
    }
}
