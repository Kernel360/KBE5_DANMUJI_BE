package com.back2basics.domain.user.controller;

import static com.back2basics.domain.inquiry.controller.code.InquiryResponseCode.INQUIRY_READ_ALL_SUCCESS;
import static com.back2basics.domain.user.controller.code.UserResponseCode.USER_CREATE_SUCCESS;
import static com.back2basics.domain.user.controller.code.UserResponseCode.USER_DELETE_SUCCESS;
import static com.back2basics.domain.user.controller.code.UserResponseCode.USER_EXISTS_SUCCESS;
import static com.back2basics.domain.user.controller.code.UserResponseCode.USER_POSITIONS_READ_SUCCESS;
import static com.back2basics.domain.user.controller.code.UserResponseCode.USER_READ_ALL_SUCCESS;
import static com.back2basics.domain.user.controller.code.UserResponseCode.USER_READ_SUCCESS;
import static com.back2basics.domain.user.controller.code.UserResponseCode.USER_UPDATE_ROLE_SUCCESS;
import static com.back2basics.domain.user.controller.code.UserResponseCode.USER_UPDATE_SUCCESS;

import com.back2basics.domain.user.dto.request.SearchUserRequest;
import com.back2basics.domain.user.dto.request.UserCreateRequest;
import com.back2basics.domain.user.dto.request.UserUpdateRequest;
import com.back2basics.domain.user.dto.response.UserCreateResponse;
import com.back2basics.domain.user.dto.response.UserInfoResponse;
import com.back2basics.domain.user.dto.response.UserPositionResponse;
import com.back2basics.domain.user.dto.response.UserSimpleResponse;
import com.back2basics.global.response.result.ApiResponse;
import com.back2basics.security.model.CustomUserDetails;
import com.back2basics.user.port.in.CreateUserUseCase;
import com.back2basics.user.port.in.DeleteUserUseCase;
import com.back2basics.user.port.in.ResetPasswordUseCase;
import com.back2basics.user.port.in.UpdateUserUseCase;
import com.back2basics.user.port.in.UserQueryUseCase;
import com.back2basics.user.service.result.UserCreateResult;
import com.back2basics.user.service.result.UserInfoResult;
import com.back2basics.user.service.result.UserPositionResult;
import com.back2basics.user.service.result.UserSimpleResult;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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

    @PostMapping("/init")
    public ResponseEntity<ApiResponse<UserCreateResponse>> createUser(
        @RequestBody @Valid UserCreateRequest request) {

        UserCreateResult result = createUserUseCase.create(request.toCommand());
        return ApiResponse.success(USER_CREATE_SUCCESS,
            UserCreateResponse.from(result));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<UserCreateResponse>> createUser(
        @AuthenticationPrincipal CustomUserDetails customUserDetails,
        @RequestBody @Valid UserCreateRequest request) {

        UserCreateResult result = createUserUseCase.create(request.toCommand(),
            customUserDetails.getId());
        return ApiResponse.success(USER_CREATE_SUCCESS,
            UserCreateResponse.from(result));
    }

    @GetMapping("/positions")
    public ResponseEntity<ApiResponse<List<UserPositionResponse>>> getAllPositions() {
        List<UserPositionResult> resultList = userQueryUseCase.getAllPositions();
        List<UserPositionResponse> responseList = resultList.stream()
            .map(UserPositionResponse::from)
            .toList();
        return ApiResponse.success(USER_POSITIONS_READ_SUCCESS, responseList);
    }

    @GetMapping("/filtering")
    public ResponseEntity<ApiResponse<Page<UserInfoResponse>>> getUserFiltering(
        @Valid @ModelAttribute SearchUserRequest request,
        @PageableDefault(
            page = 0,
            size = 10,
            sort = "id",
            direction = Sort.Direction.DESC
        )
        Pageable pageable) {

        Page<UserInfoResult> users = userQueryUseCase.searchUsers(request.toCommand(),
            pageable);
        return ApiResponse.success(INQUIRY_READ_ALL_SUCCESS,
            users.map(UserInfoResponse::from));
    }

    @GetMapping("/exists/{username}")
    public ResponseEntity<ApiResponse<Boolean>> checkUsernameExists(@PathVariable String username) {
        boolean exists = userQueryUseCase.existsByUsername(username);
        return ApiResponse.success(USER_EXISTS_SUCCESS, exists);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<ApiResponse<Void>> updateUser(
        @AuthenticationPrincipal CustomUserDetails customUserDetails,
        @RequestBody @Valid UserUpdateRequest request, @PathVariable Long userId) {

        updateUserUseCase.update(userId, request.toCommand(), customUserDetails.getId());
        return ApiResponse.success(USER_UPDATE_SUCCESS);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponse<Void>> deleteUser(
        @AuthenticationPrincipal CustomUserDetails customUserDetails, @PathVariable Long userId) {

        deleteUserUseCase.delete(userId, customUserDetails.getId());
        return ApiResponse.success(USER_DELETE_SUCCESS);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse<UserInfoResponse>> getUser(@PathVariable Long userId) {
        UserInfoResult result = userQueryUseCase.getUserInfo(userId);
        return ApiResponse.success(USER_READ_SUCCESS, UserInfoResponse.from(result));
    }

    @PutMapping("/password/reset/{userId}")
    public ResponseEntity<ApiResponse<String>> resetPassword(
        @AuthenticationPrincipal CustomUserDetails customUserDetails, @PathVariable Long userId) {

        String generatedPassword = resetPasswordUseCase.resetByAdmin(userId,
            customUserDetails.getId());
        return ApiResponse.success(USER_CREATE_SUCCESS, generatedPassword);
    }

    @GetMapping("/deletedUsers")
    public ResponseEntity<ApiResponse<Page<UserSimpleResponse>>> getDeletedUsers(
        @PageableDefault Pageable pageable) {
        Page<UserSimpleResult> resultList = userQueryUseCase.getDeletedUsers(pageable);
        Page<UserSimpleResponse> responseList = resultList.map(UserSimpleResponse::from);
        return ApiResponse.success(USER_READ_ALL_SUCCESS, responseList);
    }

    @GetMapping("/allUsers")
    public ResponseEntity<ApiResponse<Page<UserInfoResponse>>> getAllUsers(
        @PageableDefault Pageable pageable) {
        Page<UserInfoResult> resultList = userQueryUseCase.getAllUsers(pageable);
        Page<UserInfoResponse> responseList = resultList.map(UserInfoResponse::from);
        return ApiResponse.success(USER_READ_ALL_SUCCESS, responseList);
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<UserInfoResponse>>> getAllUsersWithoutPagenation() {
        List<UserInfoResult> resultList = userQueryUseCase.getAll();
        List<UserInfoResponse> responseList = resultList.stream().map(UserInfoResponse::from)
            .toList();
        return ApiResponse.success(USER_READ_ALL_SUCCESS, responseList);
    }

    @GetMapping("/counts")
    public ResponseEntity<ApiResponse<Long>> getUserCounts() {
        Long counts = userQueryUseCase.getUserCounts();
        return ApiResponse.success(USER_READ_SUCCESS, counts);
    }

    @PutMapping("/{userId}/role/{role}")
    public ResponseEntity<ApiResponse<Void>> updateUserRole(
        @AuthenticationPrincipal CustomUserDetails customUserDetails,
        @PathVariable Long userId, @PathVariable String role) {

        updateUserUseCase.updateUserRole(userId, role, customUserDetails.getId());
        return ApiResponse.success(USER_UPDATE_ROLE_SUCCESS);
    }
}
