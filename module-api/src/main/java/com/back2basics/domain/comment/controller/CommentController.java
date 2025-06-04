package com.back2basics.domain.comment.controller;

import com.back2basics.comment.port.in.CommentCreateUseCase;
import com.back2basics.comment.port.in.CommentDeleteUseCase;
import com.back2basics.comment.port.in.CommentUpdateUseCase;
import com.back2basics.domain.comment.controller.code.CommentResponseCode;
import com.back2basics.domain.comment.dto.request.CommentCreateRequest;
import com.back2basics.domain.comment.dto.request.CommentUpdateRequest;
import com.back2basics.domain.comment.swagger.CommentApiDocs;
import com.back2basics.global.response.result.ApiResponse;
import com.back2basics.security.model.CustomUserDetails;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
        @AuthenticationPrincipal CustomUserDetails customUserDetails,
        @RequestBody @Valid CommentCreateRequest request) {
        Long createdId = createCommentUseCase.createComment(customUserDetails.getId(),
            request.toCommand());
        return ApiResponse.success(CommentResponseCode.COMMENT_CREATE_SUCCESS, createdId);
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<ApiResponse<Void>> updateComment(
        @AuthenticationPrincipal CustomUserDetails customUserDetails,
        @PathVariable Long commentId,
        @Valid @RequestBody CommentUpdateRequest request
    ) {
        updateCommentUseCase.updateComment(customUserDetails.getId(), commentId,
            request.toCommand());
        return ApiResponse.success(CommentResponseCode.COMMENT_UPDATE_SUCCESS);
    }

    @DeleteMapping("/delete/{commentId}")
    public ResponseEntity<ApiResponse<Void>> deleteComment(
        @AuthenticationPrincipal CustomUserDetails customUserDetails,
        @PathVariable Long commentId
    ) {
        commentDeleteUseCase.deleteComment(customUserDetails.getId(), commentId);
        return ApiResponse.success(CommentResponseCode.COMMENT_DELETE_SUCCESS);
    }

}
