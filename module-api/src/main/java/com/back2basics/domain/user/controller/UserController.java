package com.back2basics.domain.user.controller;

import static com.back2basics.domain.user.controller.code.UserResponseCode.USER_CHANGE_PASSWORD_SUCCESS;
import static com.back2basics.domain.user.controller.code.UserResponseCode.USER_READ_SUCCESS;
import static com.back2basics.domain.user.controller.code.UserResponseCode.USER_RESET_PASSWORD_SUCCESS;
import static com.back2basics.domain.user.controller.code.UserResponseCode.USER_SEND_MAIL_SUCCESS;

import com.back2basics.domain.user.dto.request.ChangePasswordRequest;
import com.back2basics.domain.user.dto.request.ResetPasswordByTokenRequest;
import com.back2basics.domain.user.dto.request.ResetPasswordRequest;
import com.back2basics.domain.user.dto.request.SendMailRequest;
import com.back2basics.domain.user.dto.response.UserInfoResponse;
import com.back2basics.global.response.result.ApiResponse;
import com.back2basics.security.model.CustomUserDetails;
import com.back2basics.user.port.in.ChangePasswordUseCase;
import com.back2basics.user.port.in.ResetPasswordByTokenUseCase;
import com.back2basics.user.port.in.ResetPasswordUseCase;
import com.back2basics.user.port.in.SendMailUseCase;
import com.back2basics.user.port.in.UserQueryUseCase;
import com.back2basics.user.port.in.command.ChangePasswordCommand;
import com.back2basics.user.port.in.command.ResetPasswordCommand;
import com.back2basics.user.service.result.UserInfoResult;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserQueryUseCase userQueryUseCase;
    private final ChangePasswordUseCase changePasswordUseCase;
    private final ResetPasswordUseCase resetPasswordUseCase;
    private final SendMailUseCase sendMailUseCase;
    private final ResetPasswordByTokenUseCase resetPasswordByTokenUseCase;

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<UserInfoResponse>> getMyInfo(
        @AuthenticationPrincipal CustomUserDetails userDetails) {
        UserInfoResult result = userQueryUseCase.getUserInfo(userDetails.getId());
        return ApiResponse.success(USER_READ_SUCCESS, UserInfoResponse.from(result));
    }
    
    @PutMapping("/password/change")
    public ResponseEntity<ApiResponse<Void>> changePassword(
        @Valid @RequestBody ChangePasswordRequest request,
        @AuthenticationPrincipal CustomUserDetails userDetails) {
        ChangePasswordCommand command = new ChangePasswordCommand(
            request.currentPassword(), request.newPassword());

        changePasswordUseCase.change(userDetails.getId(), command);
        return ApiResponse.success(USER_CHANGE_PASSWORD_SUCCESS);
    }

    @PostMapping("/password/reset-mail/request")
    public ResponseEntity<ApiResponse<Void>> sendMail(
        @Valid @RequestBody SendMailRequest request) {
        sendMailUseCase.send(request.toCommand());
        return ApiResponse.success(USER_SEND_MAIL_SUCCESS);
    }

    @PostMapping("/password/reset-mail/confirm")
    public ResponseEntity<ApiResponse<Void>> resetPasswordMail(
        @Valid @RequestBody ResetPasswordByTokenRequest request) {
        resetPasswordByTokenUseCase.change(request.toCommand());
        return ApiResponse.success(USER_CHANGE_PASSWORD_SUCCESS);
    }

    @PutMapping("/password/reset")
    public ResponseEntity<ApiResponse<Void>> resetPassword(
        @Valid @RequestBody ResetPasswordRequest request,
        @AuthenticationPrincipal CustomUserDetails userDetails) {
        ResetPasswordCommand command = new ResetPasswordCommand(request.newPassword());

        resetPasswordUseCase.reset(userDetails.getId(), command);
        return ApiResponse.success(USER_RESET_PASSWORD_SUCCESS);
    }

}
