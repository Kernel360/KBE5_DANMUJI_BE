package com.back2basics.history.model;

import com.back2basics.board.post.model.Post;
import com.back2basics.history.port.command.HistoryRequestCommand;
import org.springframework.stereotype.Component;

@Component
public class HistoryRequestFactory {

    public static HistoryRequestCommand postCreated(Post savedPost) {
        return new HistoryRequestCommand(
            HistoryType.CREATED,
            DomainType.POST,
            savedPost.getId(),
            savedPost.getAuthorId(),
            null,
            savedPost
        );
    }

    public static HistoryRequestCommand postUpdated(Post before, Post after) {
        return new HistoryRequestCommand(
            HistoryType.UPDATED,
            DomainType.POST,
            after.getId(),
            after.getAuthorId(),
            before,
            after
        );
    }

    public static HistoryRequestCommand postDeleted(Post before, Post after, Long userId) {
        return new HistoryRequestCommand(
            HistoryType.UPDATED,
            DomainType.POST,
            after.getId(),
            userId,
            before,
            after
        );
    }

    // 기타 다른 도메인들 ... 너무 많으니까 각 클래스에서 private으로 빼기보단 빈으로 만들어주는게 낫다고 생각했음
}