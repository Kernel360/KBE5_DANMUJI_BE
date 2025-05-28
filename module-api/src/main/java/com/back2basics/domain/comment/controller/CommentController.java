package com.back2basics.domain.comment.controller;

import com.back2basics.comment.port.in.CreateCommentUseCase;
import com.back2basics.comment.port.in.DeleteCommentUseCase;
import com.back2basics.comment.port.in.UpdateCommentUseCase;
import com.back2basics.domain.comment.controller.code.CommentResponseCode;
import com.back2basics.global.response.result.ApiResponse;
import com.back2basics.service.comment.dto.CommentCreateCommand;
import com.back2basics.service.comment.dto.CommentUpdateCommand;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CreateCommentUseCase createCommentUseCase;
    private final DeleteCommentUseCase deleteCommentUseCase;
    private final UpdateCommentUseCase updateCommentUseCase;

    @PostMapping
    public ResponseEntity<ApiResponse<Long>> createComment(
        @RequestBody @Valid CommentCreateCommand command) {
        Long createdId = createCommentUseCase.createComment(command);
        return ApiResponse.success(CommentResponseCode.COMMENT_CREATE_SUCCESS, createdId);
    }

    @PatchMapping("/{commentId}")
    public ResponseEntity<ApiResponse<Void>> updateComment(
        @PathVariable Long commentId,
        @Valid @RequestBody CommentUpdateCommand command
    ) {
        updateCommentUseCase.updateComment(commentId, command);
        return ApiResponse.success(CommentResponseCode.COMMENT_UPDATE_SUCCESS);
    }

    @DeleteMapping("/delete/{commentId}")
    public ResponseEntity<ApiResponse<Void>> deleteComment(
        @PathVariable Long commentId,
        @RequestParam String requesterName
    ) {
        deleteCommentUseCase.deleteComment(commentId, requesterName);
        return ApiResponse.success(CommentResponseCode.COMMENT_DELETE_SUCCESS);
    }

}
