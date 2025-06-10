package com.back2basics.user.model;

import com.back2basics.user.port.in.command.UserCreateCommand;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Getter
public class User {

    private final Long id;

    private String username;
    private String password;
    private String name;
    private String email;
    private String phone;
    private String position;
    private Role role;
    private Long companyId;
    private LocalDateTime lastLoginAt;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
    private final LocalDateTime deletedAt;

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
        String position, Role role, Long companyId, LocalDateTime lastLoginAt,
        LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime deletedAt) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.position = position;
        this.role = role;
        this.companyId = companyId;
        this.lastLoginAt = null;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
    }

    public static User create(UserCreateCommand command, String encodedPassword) {
        return User.builder()
            .username(command.getUsername())
            .password(encodedPassword)
            .name(command.getName())
            .email(command.getEmail())
            .phone(command.getPhone())
            .position(command.getPosition())
            .role(command.getRole())
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

    public boolean validateCurrentPassword(String currentPassword,
        BCryptPasswordEncoder bCryptPasswordEncoder) {
        return bCryptPasswordEncoder.matches(currentPassword, this.password);
    }

    public void changePassword(String newPassword) {
        this.password = newPassword;
    }

    public void updateRole(Role role) {
        this.role = role;
    }

    public void updateLastLoginAt() {
        this.lastLoginAt = LocalDateTime.now();
    }
}
