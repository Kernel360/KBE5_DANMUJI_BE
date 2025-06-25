package com.back2basics.checklist.service;

import com.back2basics.checklist.model.CheckList;
import com.back2basics.checklist.port.in.GetCheckListUseCase;
import com.back2basics.checklist.port.out.CheckListQueryPort;
import com.back2basics.checklist.service.result.CheckListResult;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetCheckListService implements GetCheckListUseCase {

    private final CheckListQueryPort checkListQueryPort;

    @Override
    public List<CheckListResult> findByUserId(Long userId) {
        List<CheckList> checkLists = checkListQueryPort.findByUserId(userId);

        return checkLists.stream()
            .map(CheckListResult::from)
            .toList();
    }

    @Override
    public List<CheckListResult> findByPostId(Long postId, Long userId) {
        List<CheckList> checkLists = checkListQueryPort.findByPostId(postId, userId);
        return checkLists.stream()
            .map(CheckListResult::from)
            .toList();
    }

    @Override
    public List<CheckListResult> findByToday(Long userId) {
        List<CheckList> checkLists = checkListQueryPort.findByToday(userId);
        return checkLists.stream()
            .map(CheckListResult::from)
            .toList();
    }
}
