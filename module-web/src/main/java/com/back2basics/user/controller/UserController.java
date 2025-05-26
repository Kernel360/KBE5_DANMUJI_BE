package com.back2basics.user.controller;

import com.back2basics.port.in.user.CreateUserUseCase;
import com.back2basics.port.in.user.DeleteUserUseCase;
import com.back2basics.port.in.user.GetUserUseCase;
import com.back2basics.port.in.user.UpdateUserUseCase;
import com.back2basics.response.global.result.ApiResponse;
import com.back2basics.service.user.dto.UserCreateCommand;
import com.back2basics.service.user.dto.UserResponseDto;
import com.back2basics.service.user.dto.UserUpdateCommand;
import com.back2basics.service.user.response.UserResponseCode;
import com.back2basics.user.dto.UserCreateRequest;
import com.back2basics.user.dto.UserUpdateRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final Long userId = 1L;

    private final CreateUserUseCase createUserUseCase;
    private final UpdateUserUseCase updateUserUseCase;
    private final DeleteUserUseCase deleteUserUseCase;
    private final GetUserUseCase getUserUseCase;

    @PostMapping
    public ResponseEntity<ApiResponse<Void>> createUser(
        @RequestBody @Valid UserCreateRequest request) {
        UserCreateCommand command = new UserCreateCommand(request.getUsername(),
            request.getPassword());
        createUserUseCase.createUser(command);
        return ApiResponse.success(UserResponseCode.USER_CREATE_SUCCESS);
    }

    @PatchMapping
    public ResponseEntity<ApiResponse<Void>> updateUser(
        @RequestBody @Valid UserUpdateRequest request) {
        UserUpdateCommand command = new UserUpdateCommand(request.getUsername(),
            request.getPassword());
        updateUserUseCase.updateUser(userId, command);
        return ApiResponse.success(UserResponseCode.USER_UPDATE_SUCCESS);
    }

    @DeleteMapping
    public ResponseEntity<ApiResponse<Void>> deleteUser() {

        deleteUserUseCase.deleteUser(userId);
        return ApiResponse.success(UserResponseCode.USER_DELETE_SUCCESS);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<UserResponseDto>> getUser() {
        UserResponseDto userResponseDto = getUserUseCase.getUser(userId);
        return ApiResponse.success(UserResponseCode.USER_READ_SUCCESS, userResponseDto);
    }

}
