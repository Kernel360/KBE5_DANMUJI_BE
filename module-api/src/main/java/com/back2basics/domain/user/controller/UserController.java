package com.back2basics.domain.user.controller;

import static com.back2basics.domain.user.controller.code.UserResponseCode.USER_CHANGE_PASSWORD_SUCCESS;
import static com.back2basics.domain.user.controller.code.UserResponseCode.USER_RESET_PASSWORD_SUCCESS;

import com.back2basics.domain.user.dto.request.ChangePasswordRequest;
import com.back2basics.domain.user.dto.request.ResetPasswordRequest;
import com.back2basics.global.response.result.ApiResponse;
import com.back2basics.security.model.CustomUserDetails;
import com.back2basics.user.port.in.ChangePasswordUseCase;
import com.back2basics.user.port.in.ResetPasswordUseCase;
import com.back2basics.user.port.in.command.ChangePasswordCommand;
import com.back2basics.user.port.in.command.ResetPasswordCommand;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final ChangePasswordUseCase changePasswordUseCase;
    private final ResetPasswordUseCase resetPasswordUseCase;

    @PutMapping("/change-password")
    public ResponseEntity<ApiResponse<Void>> changePassword(
        @Valid @RequestBody ChangePasswordRequest request,
        @AuthenticationPrincipal CustomUserDetails userDetails) {
        ChangePasswordCommand command = new ChangePasswordCommand(
            request.currentPassword(), request.newPassword());

        changePasswordUseCase.change(userDetails.getId(), command);
        return ApiResponse.success(USER_CHANGE_PASSWORD_SUCCESS);
    }

    // todo: 메일 전송

    // 비밀번호 재설정
    @PutMapping("/reset-password")
    public ResponseEntity<ApiResponse<Void>> resetPassword(
        @Valid @RequestBody ResetPasswordRequest request,
        @AuthenticationPrincipal CustomUserDetails userDetails) {
        ResetPasswordCommand command = new ResetPasswordCommand(request.newPassword());

        resetPasswordUseCase.reset(userDetails.getId(), command);
        return ApiResponse.success(USER_RESET_PASSWORD_SUCCESS);
    }

}
