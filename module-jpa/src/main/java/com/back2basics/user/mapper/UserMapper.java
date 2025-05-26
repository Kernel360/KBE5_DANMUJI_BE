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
            .name(entity.getName())
            .email(entity.getEmail())
            .phone(entity.getPhone())
            .position(entity.getPosition())
            .build();
    }

    public UserEntity toEntity(User user) {
        return UserEntity.builder()
            .username(user.getUsername())
            .password(user.getPassword())
            .name(user.getName())
            .email(user.getEmail())
            .phone(user.getPhone())
            .position(user.getPosition())
            .build();
    }

}
