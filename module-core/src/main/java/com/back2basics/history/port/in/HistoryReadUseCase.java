package com.back2basics.history.port.in;

import org.springframework.data.domain.Page;

public interface HistoryReadUseCase {

    HistoryResult getHistory(Long historyId);

    Page<HistorySimpleResult> getHistories();

    Page<HistorySimpleResult> searchHistories();

}
