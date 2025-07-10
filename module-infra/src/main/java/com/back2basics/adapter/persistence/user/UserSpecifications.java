package com.back2basics.adapter.persistence.user;

import com.back2basics.adapter.persistence.user.entity.UserEntity;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

public class UserSpecifications {

    public static Specification<UserEntity> hasCompany(Long companyId) {
        return (root, query, cb) -> {
            if (companyId == null) {
                return null;
            }
            return cb.equal(root.join("company", JoinType.INNER)
                .get("id"), companyId);
        };
    }

    public static Specification<UserEntity> hasPosition(String position) {
        return (root, query, cb) -> {
            if (!StringUtils.hasText(position)) {
                return null;
            }
            return cb.equal(root.get("position"), position);
        };
    }

    public static Specification<UserEntity> hasExactName(String name) {
        return (root, query, cb) -> {
            if (!StringUtils.hasText(name)) {
                return null;
            }
            return cb.equal(root.get("name"), name);
        };
    }

    public static Specification<UserEntity> isNotDeleted() {
        return (root, query, cb) -> cb.isNull(root.get("deletedAt"));
    }
}
