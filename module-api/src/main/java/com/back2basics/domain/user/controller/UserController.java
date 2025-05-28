package com.back2basics.domain.user.controller;

import com.back2basics.domain.user.controller.code.UserResponseCode;
import com.back2basics.domain.user.dto.request.ChangePasswordRequest;
import com.back2basics.domain.user.dto.request.ResetPasswordRequest;
import com.back2basics.global.response.result.ApiResponse;
import com.back2basics.user.port.in.command.ChangePasswordCommand;
import com.back2basics.user.port.in.command.ResetPasswordCommand;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

//    private final ChangePasswordUseCase changePasswordUseCase;

    // todo: user 인증 객체 사용
    @PostMapping("/change-password/{userId}")
    public ResponseEntity<ApiResponse<Void>> changePassword(@PathVariable Long userId,
        @Valid @RequestBody ChangePasswordRequest request) {
        ChangePasswordCommand command = new ChangePasswordCommand(
            request.getCurrentPassword(), request.getNewPassword());

//        changePasswordUseCase.change(userId, command);
        return ApiResponse.success(UserResponseCode.USER_CHANGE_PASSWORD_SUCCESS);
    }

    // 비밀번호 재설정
    @PostMapping("/reset-password/{userId}")
    public ResponseEntity<ApiResponse<Void>> resetPassword(@PathVariable Long userId,
        @Valid @RequestBody ResetPasswordRequest request) {
        ResetPasswordCommand command = new ResetPasswordCommand(request.getEmail());

        return ApiResponse.success(UserResponseCode.USER_RESET_PASSWORD_SUCCESS);
    }

}
