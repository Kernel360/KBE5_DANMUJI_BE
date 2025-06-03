package com.back2basics.user.model;

import com.back2basics.user.port.in.command.UserCreateCommand;
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
    // TODO: 개발사 담당자/고객사 담당자 구분 필드 추가

    private Long companyId;

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
        String position, Role role, Long companyId) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.position = position;
        this.role = role;
        this.companyId = companyId;
    }

    public static User create(UserCreateCommand command, String encodedPassword) {
        return User.builder()
            .username(command.getUsername())
            .password(encodedPassword)
            .name(command.getName())
            .email(command.getEmail())
            .phone(command.getPhone())
            .position(command.getPosition())
            .role(Role.USER)
            .companyId(command.getCompanyId())
            .build();
    }

    public void updateUser(String username, String name, String email, String phone,
        String position, Long companyId) {
        this.username = username;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.position = position;
        this.companyId = companyId;
    }

    public boolean validateCurrentPassword(String currentPassword) {
        return this.password.matches(currentPassword);
    }

    public void changePassword(String newPassword) {
        this.password = newPassword;
    }
}
