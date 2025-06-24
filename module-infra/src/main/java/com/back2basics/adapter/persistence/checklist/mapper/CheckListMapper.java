package com.back2basics.adapter.persistence.checklist.mapper;

import com.back2basics.adapter.persistence.board.post.PostEntity;
import com.back2basics.adapter.persistence.checklist.entity.CheckListEntity;
import com.back2basics.adapter.persistence.user.entity.UserEntity;
import com.back2basics.checklist.model.CheckList;
import org.springframework.stereotype.Component;

@Component
public class CheckListMapper {

    public CheckList toDomain(CheckListEntity entity) {
        return new CheckList(entity.getId(), entity.getContent(), entity.getUser().getId(),
            entity.getPost().getId(), entity.getCheckedAt(), entity.getIsChecked());
    }

    public CheckListEntity toEntity(CheckList domain, UserEntity user, PostEntity post) {
        return new CheckListEntity(domain.getId(), domain.getContent(), user, post,
            domain.getCheckedAt(), domain.getIsChecked());
    }
}
