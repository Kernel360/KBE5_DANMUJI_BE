package com.back2basics.history.model;

import com.back2basics.history.port.in.command.HistorySearchCommand;
import com.back2basics.history.strategy.TargetDomain;
import org.springframework.stereotype.Component;

@Component
public class HistoryRequestFactory {

    // static으로 해줄 필요가 굳이 있나? 처음에는 팩토리메소드 개념 넣는다고 해서 한건데 갑자기 하다보니 굳이? 오버로딩도아니고 메소드이름도 다른데
    public static <T extends TargetDomain> HistorySearchCommand created(DomainType domainType,
        T after) {
        return new HistorySearchCommand(
            HistoryType.CREATED,
            domainType,
            after.getId(),
            after.getChangedBy(),
            "empty",
            after
        );
    }

    public static <T extends TargetDomain> HistorySearchCommand updated(DomainType domainType,
        T before,
        T after) {
        return new HistorySearchCommand(
            HistoryType.UPDATED,
            domainType,
            after.getId(),
            after.getChangedBy(),
            before,
            after
        );
    }

    public static <T extends TargetDomain> HistorySearchCommand deleted(DomainType domainType,
        T before,
        T after, Long changedBy) {
        return new HistorySearchCommand(
            HistoryType.DELETED,
            domainType,
            after.getId(),
            changedBy,
            before,
            after
        );
    }

//    public static HistoryRequestCommand postCreated(Post savedPost) {
//        return new HistoryRequestCommand(
//            HistoryType.CREATED,
//            DomainType.POST,
//            savedPost.getId(),
//            savedPost.getAuthorId(),
//            "empty",
//            savedPost
//        );
//    }
//
//    public static HistoryRequestCommand postUpdated(Post before, Post after) {
//        return new HistoryRequestCommand(
//            HistoryType.UPDATED,
//            DomainType.POST,
//            after.getId(),
//            after.getAuthorId(),
//            before,
//            after
//        );
//    }
//
//    public static HistoryRequestCommand postDeleted(Post before, Post after, Long userId) {
//        return new HistoryRequestCommand(
//            HistoryType.DELETED,
//            DomainType.POST,
//            after.getId(),
//            userId,
//            before,
//            after
//        );
//    }

}