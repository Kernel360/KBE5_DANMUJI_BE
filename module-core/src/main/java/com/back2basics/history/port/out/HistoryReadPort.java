package com.back2basics.history.port.out;

import com.back2basics.history.service.result.HistoryDetailResult;
import com.back2basics.history.service.result.HistorySimpleResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface HistoryReadPort {

    HistoryDetailResult getHistory(String id);

    Page<HistorySimpleResult> getHistories(Pageable pageable);
}
