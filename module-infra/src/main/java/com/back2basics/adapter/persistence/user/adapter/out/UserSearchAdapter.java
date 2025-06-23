package com.back2basics.adapter.persistence.user.adapter.out;

import static com.back2basics.adapter.persistence.user.entity.QUserEntity.userEntity;

import com.back2basics.adapter.persistence.user.mapper.UserMapper;
import com.back2basics.user.model.User;
import com.back2basics.user.port.out.UserSearchPort;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserSearchAdapter implements UserSearchPort {

    private final JPAQueryFactory queryFactory;
    private final UserMapper userMapper;

    @Override
    public List<User> searchByUsername(String username) {
        return queryFactory
            .select(userEntity)
            .from(userEntity)
            .where(userEntity.username.startsWithIgnoreCase(username))
            .stream()
            .map(userMapper::toDomain)
            .toList();
    }
}