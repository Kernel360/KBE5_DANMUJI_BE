package com.back2basics.domain.user.controller;

import com.back2basics.domain.user.controller.code.UserResponseCode;
import com.back2basics.domain.user.dto.request.UserCreateRequest;
import com.back2basics.domain.user.dto.request.UserUpdateRequest;
import com.back2basics.domain.user.dto.response.UserCreateResponse;
import com.back2basics.domain.user.dto.response.UserInfoResponse;
import com.back2basics.global.response.result.ApiResponse;
import com.back2basics.user.port.in.CreateUserUseCase;
import com.back2basics.user.port.in.DeleteUserUseCase;
import com.back2basics.user.port.in.ResetPasswordUseCase;
import com.back2basics.user.port.in.UpdateUserUseCase;
import com.back2basics.user.port.in.UserQueryUseCase;
import com.back2basics.user.port.in.command.UserUpdateCommand;
import com.back2basics.user.service.result.UserCreateResult;
import com.back2basics.user.service.result.UserInfoResult;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class AdminController {

    private final CreateUserUseCase createUserUseCase;
    private final UpdateUserUseCase updateUserUseCase;
    private final DeleteUserUseCase deleteUserUseCase;
    private final UserQueryUseCase userQueryUseCase;
    private final ResetPasswordUseCase resetPasswordUseCase;

    @PostMapping
    public ResponseEntity<ApiResponse<UserCreateResponse>> createUser(
        @RequestBody @Valid UserCreateRequest request) {
        UserCreateResult result = createUserUseCase.create(request.toCommand());
        return ApiResponse.success(UserResponseCode.USER_CREATE_SUCCESS,
            UserCreateResponse.from(result));
    }

    @PutMapping("/{userId}")
    public ResponseEntity<ApiResponse<Void>> updateUser(
        @RequestBody @Valid UserUpdateRequest request, @PathVariable Long userId) {
        UserUpdateCommand command = new UserUpdateCommand(request.username(),
            request.name(), request.email(), request.phone(), request.position());
        updateUserUseCase.update(userId, command);
        return ApiResponse.success(UserResponseCode.USER_UPDATE_SUCCESS);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponse<Void>> deleteUser(@PathVariable Long userId) {
        deleteUserUseCase.delete(userId);
        return ApiResponse.success(UserResponseCode.USER_DELETE_SUCCESS);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse<UserInfoResponse>> getUser(@PathVariable Long userId) {
        UserInfoResult result = userQueryUseCase.getUserInfo(userId);
        return ApiResponse.success(UserResponseCode.USER_READ_SUCCESS,
            UserInfoResponse.from(result));
    }

    @PutMapping("/reset-password/{userId}")
    public ResponseEntity<ApiResponse<String>> resetPassword(@PathVariable Long userId) {
        String generatedPassword = resetPasswordUseCase.resetByAdmin(userId);
        return ApiResponse.success(UserResponseCode.USER_CREATE_SUCCESS, generatedPassword);
    }
}
