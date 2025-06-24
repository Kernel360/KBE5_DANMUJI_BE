package com.back2basics.adapter.persistence.checklist.adapter;

import static com.back2basics.infra.exception.checklist.CheckListErrorCode.CHECK_LIST_NOT_FOUND;

import com.back2basics.adapter.persistence.checklist.entity.CheckListEntity;
import com.back2basics.adapter.persistence.checklist.mapper.CheckListMapper;
import com.back2basics.adapter.persistence.checklist.repository.CheckListEntityRepository;
import com.back2basics.checklist.model.CheckList;
import com.back2basics.checklist.port.out.CheckListQueryPort;
import com.back2basics.infra.exception.checklist.CheckListException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CheckListQueryAdapter implements CheckListQueryPort {

    private final CheckListMapper mapper;
    private final CheckListEntityRepository checkListEntityRepository;

    @Override
    public CheckList findById(Long id) {
        CheckListEntity checkListEntity = checkListEntityRepository.findById(id).orElseThrow(
            () -> new CheckListException(CHECK_LIST_NOT_FOUND));
        return mapper.toDomain(checkListEntity);
    }

    @Override
    public void delete(Long checkListId) {
        checkListEntityRepository.deleteById(checkListId);
    }
}
