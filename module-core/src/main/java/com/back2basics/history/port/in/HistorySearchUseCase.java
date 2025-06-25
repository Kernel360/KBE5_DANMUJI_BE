package com.back2basics.history.port.in;

import com.back2basics.history.port.in.command.HistorySearchCommand;
import com.back2basics.history.service.result.HistorySimpleResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface HistorySearchUseCase {

    Page<HistorySimpleResult> searchHistories(Long userId, HistorySearchCommand command,
        Pageable pageable);
}
