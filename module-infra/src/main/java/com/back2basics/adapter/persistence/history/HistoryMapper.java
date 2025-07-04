package com.back2basics.adapter.persistence.history;

import com.back2basics.history.model.History;
import com.back2basics.history.service.result.HistoryDetailResult;
import com.back2basics.history.service.result.HistorySimpleResult;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;

@Component
public class HistoryMapper {

    public HistoryDocument toDocument(History history) {
        return new HistoryDocument(
            new ObjectId(),
            history.getHistoryType(),
            history.getDomainType(),
            String.valueOf(history.getDomainId()),
            history.getChangedAt(),
            history.getChangerId(),
            history.getChangerName(),
            history.getChangerUsername(),
            history.getChangerRole(),
            history.getBefore(),
            history.getAfter(),
            history.getCreatedAt(),
            history.getMessage()
        );
    }

    public HistorySimpleResult toSimpleResult(HistoryDocument doc) {
        return new HistorySimpleResult(
            doc.getId().toHexString(),
            doc.getHistoryType(),
            doc.getDomainType(),
            Long.valueOf(doc.getDomainId()),
            doc.getChangedAt(),
            doc.getChangerId(),
            doc.getChangerName(),
            doc.getChangerRole(),
            doc.getChangerUsername(),
            doc.getMessage()
        );
    }

    public HistoryDetailResult toDetailResult(HistoryDocument doc) {
        return new HistoryDetailResult(
            doc.getId().toHexString(),
            doc.getHistoryType(),
            doc.getDomainType(),
            Long.valueOf(doc.getDomainId()),
            doc.getChangedAt(),
            doc.getChangerId(),
            doc.getChangerName(),
            doc.getChangerUsername(),
            doc.getChangerRole(),
            doc.getBefore(),
            doc.getAfter(),
            doc.getCreatedAt(),
            doc.getMessage()
        );
    }
}
