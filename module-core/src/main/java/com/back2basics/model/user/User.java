package com.back2basics.model.user;

import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import lombok.Builder;
import lombok.Getter;

@Getter
public class User implements Serializable {

  private Long id;

  @NotNull
  private String username;
  @NotNull
  private String password;

  @Builder
  public User(String username, String password) {
    this.username = username;
    this.password = password;
  }

  public void updateUsername(String username) {
    this.username = username;
  }

  public void updatePassword(String password) {
    this.password = password;
  }

  public void updateUser(String username, String password) {
    this.username = username;
    this.password = password;
  }

}
