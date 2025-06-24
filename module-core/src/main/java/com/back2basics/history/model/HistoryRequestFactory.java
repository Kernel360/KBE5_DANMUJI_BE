package com.back2basics.history.model;

import com.back2basics.history.port.in.command.HistoryCreateCommand;
import com.back2basics.history.strategy.TargetDomain;
import com.back2basics.user.model.User;
import org.springframework.stereotype.Component;

@Component
public class HistoryRequestFactory {

    // static으로 해줄 필요가 굳이 있나? 처음에는 팩토리메소드 개념 넣는다고 해서 한건데 갑자기 하다보니 굳이? 오버로딩도아니고 메소드이름도 다른데
    public static <T extends TargetDomain> HistoryCreateCommand created(DomainType domainType,
        User user,
        T after, String message) {
        return new HistoryCreateCommand(
            HistoryType.CREATED,
            domainType,
            after.getId(),
            user.getId(),
            user.getName(),
            user.getUsername(),
            user.getRole(),
            "empty",
            after,
            message
        );
    }

    public static <T extends TargetDomain> HistoryCreateCommand updated(DomainType domainType,
        User user,
        T before,
        T after, String message) {
        return new HistoryCreateCommand(
            HistoryType.UPDATED,
            domainType,
            after.getId(),
            user.getId(),
            user.getName(),
            user.getUsername(),
            user.getRole(),
            before,
            after,
            message
        );
    }

    public static <T extends TargetDomain> HistoryCreateCommand deleted(DomainType domainType,
        User user,
        T before,
        T after, String message) {
        return new HistoryCreateCommand(
            HistoryType.DELETED,
            domainType,
            after.getId(),
            user.getId(),
            user.getName(),
            user.getUsername(),
            user.getRole(),
            before,
            after,
            message
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