package com.back2basics.history.port.in;

import com.back2basics.history.service.result.HistoryDetailResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface HistoryReadUseCase {

    HistoryDetailResult getHistoryById(Long userId, Long historyId);

    Page<HistorySimpleResult> getAllHistories(Long userId, Pageable pageable);

    Page<HistorySimpleResult> searchHistories(Long userId, HistorySearchCommand command,
        Pageable pageable);

}
