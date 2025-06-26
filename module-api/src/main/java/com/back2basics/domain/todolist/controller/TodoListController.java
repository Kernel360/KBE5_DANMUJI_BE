package com.back2basics.domain.todolist.controller;

import static com.back2basics.domain.todolist.controller.code.TodoListResponseCode.TODO_LIST_CREATE_SUCCESS;
import static com.back2basics.domain.todolist.controller.code.TodoListResponseCode.TODO_LIST_DELETE_SUCCESS;
import static com.back2basics.domain.todolist.controller.code.TodoListResponseCode.TODO_LIST_READ_ALL_SUCCESS;
import static com.back2basics.domain.todolist.controller.code.TodoListResponseCode.TODO_LIST_UPDATE_SUCCESS;

import com.back2basics.domain.todolist.dto.request.CreateTodoListRequest;
import com.back2basics.domain.todolist.dto.response.TodoListResponse;
import com.back2basics.global.response.result.ApiResponse;
import com.back2basics.security.model.CustomUserDetails;
import com.back2basics.todolist.port.in.CreateTodoListUseCase;
import com.back2basics.todolist.port.in.DeleteTodoListUseCase;
import com.back2basics.todolist.port.in.GetTodoListUseCase;
import com.back2basics.todolist.port.in.UpdateTodoListUseCase;
import com.back2basics.todolist.service.result.TodoListResult;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/todolist")
@RequiredArgsConstructor
public class TodoListController {

    private final CreateTodoListUseCase createTodoListUseCase;
    private final UpdateTodoListUseCase updateTodoListUseCase;
    private final DeleteTodoListUseCase deleteTodoListUseCase;
    private final GetTodoListUseCase getTodoListUseCase;

    @PostMapping
    public ResponseEntity<ApiResponse<Void>> create(
        @AuthenticationPrincipal
        CustomUserDetails userDetails, @RequestBody CreateTodoListRequest request) {
        createTodoListUseCase.create(userDetails.getId(), request.toCommand());
        return ApiResponse.success(TODO_LIST_CREATE_SUCCESS);
    }

    @PutMapping("/{todoListId}")
    public ResponseEntity<ApiResponse<Void>> update(
        @AuthenticationPrincipal CustomUserDetails userDetails,
        @PathVariable Long todoListId,
        @RequestBody CreateTodoListRequest request) {
        updateTodoListUseCase.update(todoListId, request.toCommand(), userDetails.getId());
        return ApiResponse.success(TODO_LIST_UPDATE_SUCCESS);
    }

    @PutMapping("/{todoListId}/check")
    public ResponseEntity<ApiResponse<Void>> check(
        @AuthenticationPrincipal CustomUserDetails userDetails,
        @PathVariable Long todoListId) {
        updateTodoListUseCase.check(todoListId, userDetails.getId());
        return ApiResponse.success(TODO_LIST_UPDATE_SUCCESS);
    }

    @DeleteMapping("/{todoListId}")
    public ResponseEntity<ApiResponse<Void>> delete(
        @AuthenticationPrincipal CustomUserDetails userDetails,
        @PathVariable Long todoListId) {
        deleteTodoListUseCase.delete(todoListId, userDetails.getId());
        return ApiResponse.success(TODO_LIST_DELETE_SUCCESS);
    }

    // 전체
    @GetMapping
    public ResponseEntity<ApiResponse<List<TodoListResponse>>> getByUserId(
        @AuthenticationPrincipal CustomUserDetails userDetails) {

        List<TodoListResult> results = getTodoListUseCase.findByUserId(userDetails.getId());
        List<TodoListResponse> responses = results.stream().map(TodoListResponse::from).toList();
        return ApiResponse.success(TODO_LIST_READ_ALL_SUCCESS, responses);
    }

    // 오늘자
    @GetMapping("/today")
    public ResponseEntity<ApiResponse<List<TodoListResponse>>> getTodayByUserId(
        @AuthenticationPrincipal CustomUserDetails userDetails) {
        List<TodoListResult> results = getTodoListUseCase.findByToday(userDetails.getId());
        List<TodoListResponse> responses = results.stream().map(TodoListResponse::from).toList();
        return ApiResponse.success(TODO_LIST_READ_ALL_SUCCESS, responses);
    }
}
