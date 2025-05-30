package com.back2basics.adapter.persistence.user.mapper;

import com.back2basics.adapter.persistence.user.entity.UserEntity;
import com.back2basics.user.model.User;
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
            .build();
    }

    public UserEntity toEntity(User user) {
        return UserEntity.builder()
            .id(user.getId())
            .username(user.getUsername())
            .password(user.getPassword())
            .name(user.getName())
            .email(user.getEmail())
            .phone(user.getPhone())
            .position(user.getPosition())
            .role(user.getRole())
            .build();
    }

}
