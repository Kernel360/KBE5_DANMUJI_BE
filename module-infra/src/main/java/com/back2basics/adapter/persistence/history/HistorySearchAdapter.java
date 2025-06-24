package com.back2basics.adapter.persistence.history;

import com.back2basics.history.port.in.command.HistoryCreateCommand;
import com.back2basics.history.port.out.HistorySearchPort;
import com.back2basics.history.service.result.HistorySimpleResult;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class HistorySearchAdapter implements HistorySearchPort {

    @Override
    public Page<HistorySimpleResult> searchHistories(HistoryCreateCommand command,
        Pageable pageable) {
        return null;
    }
}
