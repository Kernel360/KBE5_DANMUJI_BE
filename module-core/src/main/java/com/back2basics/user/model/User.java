package com.back2basics.user.model;

import com.back2basics.company.model.Company;
import lombok.Builder;
import lombok.Getter;

@Getter
public class User {

    private final Long id;

    private String username;
    private String password;
    private String name;
    private String email;
    private String phone;
    private String position;
    private final Role role;

    private Company company;

//    private boolean isDeleted;
//
//    public void markDeleted() {
//        this.isDeleted = true;
//    }
//
//    public boolean isDeleted() {
//        return isDeleted;
//    }

    @Builder
    public User(Long id, String username, String password, String name, String email, String phone,
        String position, Role role, Company company) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.position = position;
        this.role = role;
        this.company = company;
    }

    public void updateUser(String username, String name, String email, String phone,
        String position, Company company) {
        this.username = username;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.position = position;
        this.company = company;
    }

    public boolean validateCurrentPassword(String currentPassword) {
        return this.password.matches(currentPassword);
    }

    public void changePassword(String newPassword) {
        this.password = newPassword;
    }
}
