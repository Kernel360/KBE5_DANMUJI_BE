package com.back2basics.user.mapper;

import com.back2basics.model.user.User;
import com.back2basics.user.entity.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User toDomain(UserEntity entity) {
        return User.builder()
            .id(entity.getId())
            .username(entity.getUsername())
            .password(entity.getPassword())
            .name(entity.getName())
            .email(entity.getEmail())
            .phone(entity.getPhone())
            .position(entity.getPosition())
            .role(entity.getRole())
            .isDeleted(entity.getDeletedAt() != null)
            .build();
    }

    public UserEntity toEntity(User user) {
        UserEntity entity = UserEntity.builder()
            .id(user.getId())
            .username(user.getUsername())
            .password(user.getPassword())
            .name(user.getName())
            .email(user.getEmail())
            .phone(user.getPhone())
            .position(user.getPosition())
            .role(user.getRole())
            .build();
        if (user.isDeleted()) {
            entity.markDeleted();
        }
        return entity;
    }

}
