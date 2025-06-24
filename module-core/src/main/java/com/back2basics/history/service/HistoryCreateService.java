package com.back2basics.history.service;

import com.back2basics.history.model.History;
import com.back2basics.history.port.in.command.HistoryCreateCommand;
import com.back2basics.history.port.out.HistoryCreatePort;
import java.time.LocalDateTime;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HistoryCreateService {

    private final HistoryCreatePort historyCreatePort;

    @Async
    public void create(HistoryCreateCommand command) {
        History history = History.create(
            command.historyType(),
            command.domainType(),
            command.domainId(),
            String.valueOf(command.changerId()),
            String.valueOf(command.changerName()),
            command.changerRole(),
            String.valueOf(command.changerIp()),
            Map.of("before", command.before()),
            Map.of("after", command.after()),
            LocalDateTime.now()
        );
        historyCreatePort.save(history);
    }

    @Async
    public void create(HistoryCreateCommand command, Boolean isDeleted) {
        History history = History.create(
            command.historyType(),
            command.domainType(),
            command.domainId(),
            String.valueOf(command.changerId()),
            String.valueOf(command.changerName()),
            command.changerRole(),
            String.valueOf(command.changerIp()),
            Map.of("before", command.before()),
            Map.of("isDeleted", isDeleted),
            LocalDateTime.now()
        );
        historyCreatePort.save(history);
    }

//    public void createHistory(
//        HistoryType historyType,
//        DomainType domainType, // "post", "user", "company", "project", "step" 등
//        Long domainId,
//        Long changedBy,
//        Object before,
//        Object after
//    ) {
//        History history = History.create(
//            historyType,
//            domainType,
//            domainId,
//            String.valueOf(changedBy),
//            Map.of("before", before),
//            Map.of("after", after),
//            LocalDateTime.now()
//        );
//        historyCreatePort.save(history);
//    }
//
//    public void createHistory(
//        HistoryType historyType,
//        DomainType domainType,
//        Long domainId,
//        Long changedBy,
//        Object before
//    ) {
//        History history = History.create(
//            historyType,
//            domainType,
//            domainId,
//            String.valueOf(changedBy),
//            Map.of("before", before),
//            Map.of("isDeleted", true),
//            LocalDateTime.now()
//        );
//        historyCreatePort.save(history);
//    }
}
