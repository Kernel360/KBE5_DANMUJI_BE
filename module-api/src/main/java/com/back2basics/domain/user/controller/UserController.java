package com.back2basics.domain.user.controller;

import com.back2basics.domain.user.controller.code.UserResponseCode;
import com.back2basics.domain.user.dto.request.ChangePasswordRequest;
import com.back2basics.domain.user.dto.request.ResetPasswordRequest;
import com.back2basics.global.response.result.ApiResponse;
import com.back2basics.user.port.in.ChangePasswordUseCase;
import com.back2basics.user.port.in.ResetPasswordUseCase;
import com.back2basics.user.port.in.command.ChangePasswordCommand;
import com.back2basics.user.port.in.command.ResetPasswordCommand;
import jakarta.validation.Valid;
import java.security.Principal;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
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
        @AuthenticationPrincipal Principal principal) {
        ChangePasswordCommand command = new ChangePasswordCommand(
            request.currentPassword(), request.newPassword());

        changePasswordUseCase.change(principal.getName(), command);
        return ApiResponse.success(UserResponseCode.USER_CHANGE_PASSWORD_SUCCESS);
    }

    // todo: 메일 전송

    // 비밀번호 재설정
    @PutMapping("/reset-password/{userId}")
    public ResponseEntity<ApiResponse<Void>> resetPassword(@PathVariable Long userId,
        @Valid @RequestBody ResetPasswordRequest request) {
        ResetPasswordCommand command = new ResetPasswordCommand(request.newPassword());

        resetPasswordUseCase.reset(userId, command);
        return ApiResponse.success(UserResponseCode.USER_RESET_PASSWORD_SUCCESS);
    }

}
