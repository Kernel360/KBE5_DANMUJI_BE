package com.back2basics.model.user;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
public class User {

    private Long id;

    @NotNull
    private String username;
    @NotNull
    private String password;
    @NotNull
    private String name;
    @NotNull
    private String email;
    @NotNull
    private String phone;
    @NotNull
    private String position;
    @NotNull
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

    public void updatePassword(String password) {
        this.password = password;
    }

    public void updateUser(String username, String name, String email, String phone,
        String position) {
        this.username = username;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.position = position;
    }

}
