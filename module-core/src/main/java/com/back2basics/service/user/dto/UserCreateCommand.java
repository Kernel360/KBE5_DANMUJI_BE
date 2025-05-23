package com.back2basics.service.user.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserCreateCommand {

  @NotBlank(message = "아이디는 필수입니다.")
  private String username;

  @NotBlank(message = "비밀번호는 필수입니다.")
  private String password;

  public UserCreateCommand(String username, String password) {
    this.username = username;
    this.password = password;
  }
}
