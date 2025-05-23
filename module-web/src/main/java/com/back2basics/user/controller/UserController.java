package com.back2basics.user.controller;

import com.back2basics.port.in.user.CreateUserUseCase;
import com.back2basics.response.global.result.ResultResponse;
import com.back2basics.response.user.UserResponseCode;
import com.back2basics.service.user.dto.UserCreateCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

  private final CreateUserUseCase createUserUseCase;

  @PostMapping
  public ResponseEntity<ResultResponse> createUser(UserCreateCommand command) {
    Long id = createUserUseCase.createUser(command);
    return ResponseEntity.ok(ResultResponse.of(UserResponseCode.USER_CREATE_SUCCESS, id));
  }
}
