package com.back2basics.adapter.persistence.user.mapper;

import com.back2basics.adapter.persistence.company.CompanyEntity;
import com.back2basics.adapter.persistence.user.entity.UserEntity;
import com.back2basics.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
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
            .userType(entity.getUserType())
            .companyId(entity.getCompanyEntity() != null ? entity.getCompanyEntity().getId() : null)
            .createdAt(entity.getCreatedAt())
            .updatedAt(entity.getUpdatedAt())
            .build();
    }

    public UserEntity toEntity(User user, CompanyEntity companyEntity) {
        return UserEntity.builder()
            .id(user.getId())
            .username(user.getUsername())
            .password(user.getPassword())
            .name(user.getName())
            .email(user.getEmail())
            .phone(user.getPhone())
            .position(user.getPosition())
            .role(user.getRole())
            .userType(user.getUserType())
            .companyEntity(companyEntity)
            .build();
    }

    // todo : adapter에서는 자기자신의 도메인 객체만 mpper로 전달해주고 mapper에서 연관 넣어줄지
    // todo : adapter에서 연관 객체 찾아서 mapper로는 자기자신의 도메인 객체를 그냥 전달만할지
    public UserEntity toEntity(User user) {
        CompanyEntity companyEntity = user.getCompanyId() != null
            ? CompanyEntity.builder().id(user.getCompanyId()).build()
            : null;
        return toEntity(user, companyEntity);
    }


}
