package com.back2basics.model.user;

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
