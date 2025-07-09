package com.back2basics.adapter.persistence.history;

import static com.back2basics.infra.exception.history.HistoryErrorCode.HISTORY_NOT_FOUND;

import com.back2basics.history.port.out.HistoryReadPort;
import com.back2basics.history.service.result.HistoryDetailResult;
import com.back2basics.history.service.result.HistorySimpleResult;
import com.back2basics.infra.exception.history.HistoryException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class HistoryReadAdapter implements HistoryReadPort {

    private final MongoTemplate mongoTemplate;
    private final HistoryMapper historyMapper;


    @Override
    public HistoryDetailResult getHistory(String historyId) {
        HistoryDocument document = mongoTemplate.findById(
            new ObjectId(historyId),
            HistoryDocument.class
        );

        if (document == null) {
            throw new HistoryException(HISTORY_NOT_FOUND);
        }

        return historyMapper.toDetailResult(document);
    }

    @Override
    public Page<HistorySimpleResult> getHistories(Pageable pageable) {
        Query query = new Query()
            .with(pageable)
            .with(Sort.by(Sort.Direction.DESC, "_id"));

        query.fields()
            .include("_id")
            .include("history_type")
            .include("domain_type")
            .include("domain_id")
            .include("changed_at")
            .include("changer_id")
            .include("changer_name")
            .include("changer_username")
            .include("changer_role")
            .include("message");

        List<HistoryDocument> documents = mongoTemplate.find(query, HistoryDocument.class);
        long total = mongoTemplate.count(query, HistoryDocument.class);

        List<HistorySimpleResult> results = documents.stream()
            .map(historyMapper::toSimpleResult)
            .toList();

        return new PageImpl<>(results, pageable, total);
    }
}
