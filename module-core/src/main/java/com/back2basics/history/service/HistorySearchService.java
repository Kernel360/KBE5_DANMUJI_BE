package com.back2basics.history.service;

import com.back2basics.history.port.in.HistorySearchUseCase;
import com.back2basics.history.port.in.command.HistorySearchCommand;
import com.back2basics.history.port.out.HistorySearchPort;
import com.back2basics.history.service.result.HistorySimpleResult;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HistorySearchService implements HistorySearchUseCase {

    private final HistorySearchPort historySearchPort;

    @Override
    public Page<HistorySimpleResult> searchHistories(Long userId, HistorySearchCommand command,
        Pageable pageable) {
        return historySearchPort.searchHistories(command, pageable);
    }
}
