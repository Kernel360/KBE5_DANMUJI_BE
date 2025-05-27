package com.back2basics.user.controller;

import com.back2basics.port.in.user.ChangePasswordUseCase;
import com.back2basics.response.global.result.ApiResponse;
import com.back2basics.service.user.command.UserChangePasswordCommand;
import com.back2basics.service.user.response.UserResponseCode;
import com.back2basics.user.dto.request.UserPasswordChangeRequest;
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

    private final ChangePasswordUseCase changePasswordUseCase;

    // todo: user 인증 객체 사용
    // 비밀번호 변경
    @PostMapping("/change-password/{userId}")
    public ResponseEntity<ApiResponse<Void>> changePassword(@PathVariable Long userId,
        @Valid @RequestBody UserPasswordChangeRequest request) {
        UserChangePasswordCommand command = new UserChangePasswordCommand(
            request.getCurrentPassword(), request.getNewPassword());

        changePasswordUseCase.changePassword(userId, command);
        return ApiResponse.success(UserResponseCode.USER_CHANGE_PASSWORD_SUCCESS);
    }

    // 비밀번호 찾기

}
