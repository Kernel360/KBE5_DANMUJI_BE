package com.back2basics.history.port.out;

import com.back2basics.history.model.History;

public interface HistoryCreatePort {

    void save(History history);
}
