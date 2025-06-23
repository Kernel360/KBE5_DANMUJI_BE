package com.back2basics.adapter.persistence.history;

import com.back2basics.history.model.History;
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
}
