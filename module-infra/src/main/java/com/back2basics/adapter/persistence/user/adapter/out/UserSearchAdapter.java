package com.back2basics.adapter.persistence.user.adapter.out;

import static com.back2basics.adapter.persistence.assignment.QAssignmentEntity.assignmentEntity;
import static com.back2basics.adapter.persistence.user.entity.QUserEntity.userEntity;
import static com.back2basics.infra.exception.user.UserErrorCode.USER_NOT_FOUND;

import com.back2basics.adapter.persistence.user.mapper.UserMapper;
import com.back2basics.adapter.persistence.user.repository.UserEntityRepository;
import com.back2basics.infra.exception.user.UserException;
import com.back2basics.user.model.User;
import com.back2basics.user.port.out.UserSearchPort;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserSearchAdapter implements UserSearchPort {

    private final UserEntityRepository userEntityRepository;
    private final JPAQueryFactory queryFactory;
    private final UserMapper userMapper;

    @Override
    public List<User> searchUsersByProjectId(Long projectId) {
        return queryFactory
            .selectFrom(userEntity)
            .where(
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

    @Override
    public User searchUserByUsername(String username) {
        return userEntityRepository.findByUsername(username)
            .map(userMapper::toDomain)
            .orElseThrow(() -> new UserException(USER_NOT_FOUND));
    }
}