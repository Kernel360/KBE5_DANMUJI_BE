package com.back2basics.history.service;

import com.back2basics.history.port.in.HistoryReadUseCase;
import com.back2basics.history.service.result.HistoryDetailResult;
import com.back2basics.history.service.result.HistorySimpleResult;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HistoryReadService implements HistoryReadUseCase {

    private final HistoryReadPort historyReadPort;

    @Override
    public HistoryDetailResult getHistory(Long historyId) {
        return null;
    }

    @Override
    public Page<HistorySimpleResult> getHistories() {
        return null;
    }

    @Override
    public Page<HistorySimpleResult> searchHistories() {
        return null;
    }
}
