package com.back2basics.adapter.persistence.history;

import com.back2basics.history.model.History;
import com.back2basics.history.service.result.HistoryDetailResult;
import com.back2basics.history.service.result.HistorySimpleResult;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;

@Component
public class HistoryMapper {

    public static HistoryDocument toDocument(History history) {
        return new HistoryDocument(
            new ObjectId(),
            history.getHistoryType(),
            history.getDomainType(),
            String.valueOf(history.getDomainId()),
            history.getChangedAt(),
            history.getChangedBy(),
            history.getBefore(),
            history.getAfter(),
            history.getCreatedAt()
        );
    }

    public static HistorySimpleResult toSimpleResult(HistoryDocument doc) {
        return new HistorySimpleResult(
            doc.getId().toHexString(),
            doc.getHistoryType(),
            doc.getDomainType(),
            Long.valueOf(doc.getDomainId()),
            doc.getChangedAt(),
            doc.getChangedBy()
        );
    }

    public static HistoryDetailResult toDetailResult(HistoryDocument doc) {
        return new HistoryDetailResult(
            doc.getId().toHexString(),
            doc.getHistoryType(),
            doc.getDomainType(),
            Long.valueOf(doc.getDomainId()),
            doc.getChangedAt(),
            doc.getChangedBy(),
            doc.getBefore(),
            doc.getAfter(),
            doc.getCreatedAt()
        );
    }
}
