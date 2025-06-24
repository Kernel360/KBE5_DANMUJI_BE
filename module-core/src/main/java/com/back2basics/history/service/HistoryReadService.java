package com.back2basics.history.service;

import com.back2basics.history.port.in.HistoryReadUseCase;
import com.back2basics.history.port.out.HistoryReadPort;
import com.back2basics.history.service.result.HistoryDetailResult;
import com.back2basics.history.service.result.HistorySimpleResult;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HistoryReadService implements HistoryReadUseCase {

    private final HistoryReadPort historyReadPort;

    @Override
    public HistoryDetailResult getHistoryById(Long userId, String historyId) {
        return historyReadPort.getHistory(historyId);
    }

    @Override
    public Page<HistorySimpleResult> getAllHistories(Long userId, Pageable pageable) {
        return historyReadPort.getHistories(pageable);
    }
}
