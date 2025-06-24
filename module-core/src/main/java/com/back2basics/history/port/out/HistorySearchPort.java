package com.back2basics.history.port.out;

import com.back2basics.history.port.in.command.HistorySearchCommand;
import com.back2basics.history.service.result.HistorySimpleResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface HistorySearchPort {

    Page<HistorySimpleResult> searchHistories(HistorySearchCommand command, Pageable pageable);
}
