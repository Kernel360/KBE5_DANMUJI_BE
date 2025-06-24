package com.back2basics.adapter.persistence.history;

import com.back2basics.history.port.in.command.HistorySearchCommand;
import com.back2basics.history.port.out.HistorySearchPort;
import com.back2basics.history.service.result.HistorySimpleResult;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class HistorySearchAdapter implements HistorySearchPort {

    private final MongoTemplate mongoTemplate;

    @Override
    public Page<HistorySimpleResult> searchHistories(HistorySearchCommand command,
        Pageable pageable) {
        Criteria criteria = createCriteria(command);
        Query query = (criteria == null) ? new Query() : new Query(criteria);
        query.with(pageable);

        List<HistoryDocument> documents = mongoTemplate.find(query, HistoryDocument.class);
        long total = mongoTemplate.count(new Query(criteria), HistoryDocument.class);

        List<HistorySimpleResult> results = documents.stream()
            .map(HistoryMapper::toSimpleResult)
            .toList();

        return new PageImpl<>(results, pageable, total);
    }


    private Criteria createCriteria(HistorySearchCommand command) {
        List<Criteria> filters = new ArrayList<>();

        addIfPresent(filters, "historyType", command.historyType());
        addIfPresent(filters, "domainType", command.domainType());
        addIfPresent(filters, "changedBy", command.changedBy());

        if (command.changedFrom() != null && command.changedTo() != null) {
            filters.add(Criteria.where("changed_at")
                .gte(command.changedFrom())
                .lte(command.changedTo()));
        } else if (command.changedFrom() != null) {
            filters.add(Criteria.where("changed_at").gte(command.changedFrom()));
        } else if (command.changedTo() != null) {
            filters.add(Criteria.where("changed_at").lte(command.changedTo()));
        }

        return filters.isEmpty() ? new Criteria()
            : new Criteria().andOperator(filters.toArray(new Criteria[0]));
    }

    private void addIfPresent(List<Criteria> filters, String field, Object value) {
        if (value != null) {
            filters.add(Criteria.where(field).is(value));
        }
    }
}
