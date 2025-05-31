package com.back2basics.domain.comment.controller;

import com.back2basics.comment.port.in.CommentCreateUseCase;
import com.back2basics.comment.port.in.CommentDeleteUseCase;
import com.back2basics.comment.port.in.CommentUpdateUseCase;
import com.back2basics.domain.comment.controller.code.CommentResponseCode;
import com.back2basics.domain.comment.dto.request.CommentCreateRequest;
import com.back2basics.domain.comment.dto.request.CommentDeleteRequest;
import com.back2basics.domain.comment.dto.request.CommentUpdateRequest;
import com.back2basics.domain.comment.swagger.CommentApiDocs;
import com.back2basics.global.response.result.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentController implements CommentApiDocs {

    private final CommentCreateUseCase createCommentUseCase;
    private final CommentDeleteUseCase commentDeleteUseCase;
    private final CommentUpdateUseCase updateCommentUseCase;

    @PostMapping
    public ResponseEntity<ApiResponse<Long>> createComment(
        @RequestBody @Valid CommentCreateRequest request) {
        Long createdId = createCommentUseCase.createComment(request.toCommand());
        return ApiResponse.success(CommentResponseCode.COMMENT_CREATE_SUCCESS, createdId);
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<ApiResponse<Void>> updateComment(
        @PathVariable Long commentId,
        @Valid @RequestBody CommentUpdateRequest request
    ) {
        updateCommentUseCase.updateComment(commentId, request.toCommand());
        return ApiResponse.success(CommentResponseCode.COMMENT_UPDATE_SUCCESS);
    }

    @DeleteMapping("/delete/{commentId}")
    public ResponseEntity<ApiResponse<Void>> deleteComment(
        @PathVariable Long commentId,
        @Valid @RequestBody CommentDeleteRequest request
    ) {
        Long requesterId = request.getRequesterId();
        commentDeleteUseCase.deleteComment(commentId, requesterId);
        return ApiResponse.success(CommentResponseCode.COMMENT_DELETE_SUCCESS);
    }

}
