package com.back2basics.adapter.persistence.history;

import com.back2basics.history.model.History;
import com.back2basics.history.port.out.HistoryCreatePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class HistoryCreateAdapter implements HistoryCreatePort {

    private final HistoryDocumentRepository historyRepository;
    private final HistoryMapper historyMapper;

    @Override
    public void save(History history) {
        HistoryDocument document = historyMapper.toDocument(history);
        historyRepository.save(document);
    }


}
