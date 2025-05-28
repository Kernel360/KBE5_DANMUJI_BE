package com.back2basics.user.model;

import com.back2basics.infra.exception.user.UserErrorCode;
import com.back2basics.infra.exception.user.UserException;
import lombok.Builder;
import lombok.Getter;

@Getter
public class User {

    private Long id;

    private String username;
    private String password;
    private String name;
    private String email;
    private String phone;
    private String position;
    private Role role;

    private boolean isDeleted;

    public void markDeleted() {
        this.isDeleted = true;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    @Builder
    public User(Long id, String username, String password, String name, String email, String phone,
        String position, Role role, boolean isDeleted) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.position = position;
        this.role = role;
        this.isDeleted = false;
    }

    public void updateUser(String username, String name, String email, String phone,
        String position) {
        this.username = username;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.position = position;
    }

    public void validatePassword(String inputPassword) {
        if (!this.password.equals(inputPassword)) {
            throw new UserException(UserErrorCode.PASSWORD_MISMATCH);
        }
    }

    public void changePassword(String currentPassword, String newPassword) {
        validatePassword(currentPassword);
        this.password = newPassword;
    }
}
