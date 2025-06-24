package com.back2basics.history.port.in;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface HistoryReadUseCase {

    HistoryResult getHistoryById(Long userId, Long historyId);

    Page<HistorySimpleResult> getAllHistories(Long userId, Pageable pageable);

    Page<HistorySimpleResult> searchHistories(Long userId, HistorySearchCommand command,
        Pageable pageable);

}
