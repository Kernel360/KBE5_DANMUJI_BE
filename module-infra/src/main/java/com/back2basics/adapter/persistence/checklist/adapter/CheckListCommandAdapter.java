package com.back2basics.adapter.persistence.checklist.adapter;

import com.back2basics.adapter.persistence.board.post.PostEntity;
import com.back2basics.adapter.persistence.board.post.PostEntityRepository;
import com.back2basics.adapter.persistence.checklist.entity.CheckListEntity;
import com.back2basics.adapter.persistence.checklist.mapper.CheckListMapper;
import com.back2basics.adapter.persistence.checklist.repository.CheckListEntityRepository;
import com.back2basics.adapter.persistence.user.entity.UserEntity;
import com.back2basics.adapter.persistence.user.repository.UserEntityRepository;
import com.back2basics.checklist.model.CheckList;
import com.back2basics.checklist.port.out.CheckListCommandPort;
import com.back2basics.infra.validation.validator.PostValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
@RequiredArgsConstructor
public class CheckListCommandAdapter implements CheckListCommandPort {

    private final CheckListMapper mapper;
    private final CheckListEntityRepository checkListEntityRepository;
    private final UserEntityRepository userEntityRepository;
    private final PostValidator postValidator;
    private final PostEntityRepository postEntityRepository;

    @Override
    public void save(CheckList checkList) {
        postValidator.findPost(checkList.getPostId());
        UserEntity user = userEntityRepository.getReferenceById(checkList.getUserId());
        PostEntity post = postEntityRepository.getReferenceById(checkList.getPostId());
        CheckListEntity checkListEntity = mapper.toEntity(checkList, user, post);
        checkListEntityRepository.save(checkListEntity);
    }
}
