package com.back2basics.history.port.in;

import com.back2basics.history.service.result.HistoryDetailResult;
import com.back2basics.history.service.result.HistorySimpleResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface HistoryReadUseCase {

    HistoryDetailResult getHistoryById(Long userId, String historyId);

    Page<HistorySimpleResult> getAllHistories(Long userId, Pageable pageable);

}
