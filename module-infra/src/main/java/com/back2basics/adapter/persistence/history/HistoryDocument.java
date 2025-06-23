package com.back2basics.adapter.persistence.history;

import com.back2basics.history.model.HistoryType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import java.util.Map;
import lombok.Getter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Getter
@Document("history_documents")
public class HistoryDocument {

    @Id
    private ObjectId id;

    @Field("history_type")
    private HistoryType historyType;

    @Field("domain_type")
    private String domainType; // user, project, step, post ...

    @Field("domain_id")
    private String domainId;   // 얘는 어케해야되냐 string <-> long

    @Field("changed_at")
    private LocalDateTime changedAt;

    @Field("changed_by")
    private String changedBy; // 사용자 ID 또는 이름

    @Field("before")
    private Map<String, Object> before;

    @Field("after")
    private Map<String, Object> after;

    @CreatedDate
    @Field("history_created_at")
    private LocalDateTime createdAt;

    public HistoryDocument(ObjectId id,
        HistoryType historyType,
        String domainType,
        String domainId,
        LocalDateTime changedAt,
        String changedBy,
        Map<String, Object> before,
        Map<String, Object> after, LocalDateTime createdAt) {
        this.id = id;
        this.historyType = historyType;
        this.domainType = domainType;
        this.domainId = domainId;
        this.changedAt = changedAt;
        this.changedBy = changedBy;
        this.before = before;
        this.after = after;
        this.createdAt = createdAt;
    }

    public static HistoryDocument of(HistoryType historyType,
        String domainType,
        Long domainId,
        LocalDateTime changedAt,
        String changedBy,
        Map<String, Object> before,
        Map<String, Object> after) {
        return new HistoryDocument(
            new ObjectId(),
            historyType,
            domainType,
            String.valueOf(domainId),
            changedAt,
            changedBy,
            before,
            after,
            null
        );
    }
}
