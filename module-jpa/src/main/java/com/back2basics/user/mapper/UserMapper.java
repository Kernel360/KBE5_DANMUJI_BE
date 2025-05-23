package com.back2basics.user.mapper;

import com.back2basics.model.user.User;
import com.back2basics.user.entity.UserEntity;

public class UserMapper {

	public User toDomain(UserEntity entity) {
		return User.builder()
			.username(entity.getUsername())
			.password(entity.getPassword())
			.build();
	}

	public static UserEntity toEntity(User user) {
		return UserEntity.builder()
			.username(user.getUsername())
			.password(user.getPassword())
			.build();
	}

}
