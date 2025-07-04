package com.back2basics.adapter.persistence.history;

import com.back2basics.history.port.in.command.HistorySearchCommand;
import com.back2basics.history.port.out.HistorySearchPort;
import com.back2basics.history.service.result.HistorySimpleResult;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class HistorySearchAdapter implements HistorySearchPort {

    private final MongoTemplate mongoTemplate;
    private final HistoryMapper historyMapper;

    @Override
    public Page<HistorySimpleResult> searchHistories(HistorySearchCommand command,
        Pageable pageable) {
        Criteria criteria = createCriteria(command);
        Query query = new Query(criteria);

        query.with(pageable).with(Sort.by(Sort.Direction.DESC, "history_created_at"));

        List<HistoryDocument> documents = mongoTemplate.find(query, HistoryDocument.class);

        List<HistorySimpleResult> results = documents.stream()
            .map(historyMapper::toSimpleResult)
            .toList();

        return PageableExecutionUtils.getPage(
            results,
            pageable,
            () -> mongoTemplate.count(new Query(criteria), HistoryDocument.class)
        );
    }


    private Criteria createCriteria(HistorySearchCommand command) {
        List<Criteria> filters = new ArrayList<>();

        addIfPresent(filters, "historyType", command.historyType());
        addIfPresent(filters, "domainType", command.domainType());
        addIfPresent(filters, "changerRole", command.changerRole());

        // 날짜 조건(언제부터 언제까지 발생한 이력인지)
        if (command.changedFrom() != null && command.changedTo() != null) {
            filters.add(Criteria.where("changed_at")
                .gte(command.changedFrom())
                .lte(command.changedTo()));
        } else if (command.changedFrom() != null) {
            filters.add(Criteria.where("changed_at").gte(command.changedFrom()));
        } else if (command.changedTo() != null) {
            filters.add(Criteria.where("changed_at").lte(command.changedTo()));
        }

        // 변경자 검색 조건 -> username, name 둘 다 모두 검색 가능하게끔
        if (command.changedBy() != null) {
            filters.add(new Criteria().orOperator(
                Criteria.where("changer_username").is(command.changedBy()),
                Criteria.where("changer_name").is(command.changedBy())
            ));
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
