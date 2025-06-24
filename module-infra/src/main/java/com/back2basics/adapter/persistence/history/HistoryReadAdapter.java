package com.back2basics.adapter.persistence.history;

import com.back2basics.history.port.out.HistoryReadPort;
import com.back2basics.history.service.result.HistoryDetailResult;
import com.back2basics.history.service.result.HistorySimpleResult;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class HistoryReadAdapter implements HistoryReadPort {

    private final MongoTemplate mongoTemplate;


    @Override
    public HistoryDetailResult getHistory(String historyId) {
        HistoryDocument document = mongoTemplate.findById(
            new ObjectId(historyId),
            HistoryDocument.class
        );

        if (document == null) {
            throw new HistoryException(HistoryErrorCode.HISTORY_NOT_FOUND);
        }

        return HistoryMapper.toDetailResult(document);
    }

    @Override
    public Page<HistorySimpleResult> getHistories(Pageable pageable) {
        Query query = new Query().with(pageable);
        List<HistoryDocument> documents = mongoTemplate.find(query, HistoryDocument.class);
        long total = mongoTemplate.count(new Query(), HistoryDocument.class);

        List<HistorySimpleResult> results = documents.stream()
            .map(HistoryMapper::toSimpleResult)
            .toList();

        return new PageImpl<>(results, pageable, total);
    }
}
