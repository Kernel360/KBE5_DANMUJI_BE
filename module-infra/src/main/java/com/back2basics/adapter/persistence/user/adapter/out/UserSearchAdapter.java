package com.back2basics.adapter.persistence.user.adapter.out;

import static com.back2basics.adapter.persistence.user.entity.QUserEntity.userEntity;

import com.back2basics.user.port.out.UserSearchPort;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserSearchAdapter implements UserSearchPort {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<String> searchByUsername(String username) {
        return queryFactory
            .select(userEntity.username)
            .from(userEntity)
            .where(userEntity.username.startsWithIgnoreCase(username))
            .limit(10) // username이 같은 애들이 얼마나 많을지..?
            .fetch();
    }
}