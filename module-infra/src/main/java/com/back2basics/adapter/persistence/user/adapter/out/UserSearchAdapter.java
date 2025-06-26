package com.back2basics.adapter.persistence.user.adapter.out;

import static com.back2basics.adapter.persistence.assignment.QAssignmentEntity.assignmentEntity;
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
    public List<User> searchByUsernameAndProjectId(String username, Long projectId) {
        return queryFactory
            .selectFrom(userEntity)
            .where(
                userEntity.username.eq(username),
                queryFactory
                    .selectOne()
                    .from(assignmentEntity)
                    .where(
                        assignmentEntity.user.id.eq(userEntity.id),
                        assignmentEntity.project.id.eq(projectId)
                    )
                    .exists()
            )
            .stream()
            .map(userMapper::toDomain)
            .toList();
    }
}