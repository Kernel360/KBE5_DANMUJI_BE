package com.back2basics.adapter.persistence.user;

import com.back2basics.adapter.persistence.user.entity.UserEntity;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

public class UserSpecifications {

    public static Specification<UserEntity> hasCompany(Long companyId) {
        return (root, query, cb) -> {
            return cb.equal(root.join("company", JoinType.INNER).get("id"), companyId);
        };
    }

    public static Specification<UserEntity> hasPosition(String position) {
        return (root, query, cb) -> cb.equal(root.get("position"), position);
    }

    /**
     * 이름 정확 일치 (선택)
     */
    public static Specification<UserEntity> hasExactName(String name) {
        return StringUtils.hasText(name) ? (root, q, cb) -> cb.equal(root.get("name"), name) : null;
    }
}
