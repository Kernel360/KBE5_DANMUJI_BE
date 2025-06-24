package com.back2basics.history.service;

import com.back2basics.history.model.DomainType;
import com.back2basics.history.model.HistoryRequestFactory;
import com.back2basics.history.strategy.TargetDomain;
import com.back2basics.user.model.User;
import com.back2basics.user.port.out.UserQueryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HistoryLogService {

    private final UserQueryPort userQueryPort;
    private final HistoryCreateService historyCreateService;


    public <T extends TargetDomain> void logCreated(DomainType domainType, Long userId, T after,
        String message) {
        User user = userQueryPort.findById(userId);
        historyCreateService.create(
            HistoryRequestFactory.created(domainType, user, after, message));
    }

    public <T extends TargetDomain> void logUpdated(DomainType domainType, Long userId, T before,
        T after, String message) {
        User user = userQueryPort.findById(userId);
        historyCreateService.create(
            HistoryRequestFactory.updated(domainType, user, before, after, message));
    }

    public <T extends TargetDomain> void logDeleted(DomainType domainType, Long userId,
        T after, String message) {
        User user = userQueryPort.findById(userId);
        historyCreateService.create(
            HistoryRequestFactory.deleted(domainType, user, after, message));
    }
}