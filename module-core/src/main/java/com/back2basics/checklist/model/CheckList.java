package com.back2basics.checklist.model;

import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class CheckList {

    private final Long id;
    private String content;
    private final Long userId;
    private final Long postId;
    private final LocalDateTime createdAt;
    private LocalDateTime checkedAt;
    private Boolean isChecked;

    public CheckList(Long id, String content, Long userId, Long postId, LocalDateTime createdAt,
        LocalDateTime checkedAt,
        Boolean isChecked) {
        this.id = id;
        this.content = content;
        this.userId = userId;
        this.postId = postId;
        this.createdAt = createdAt;
        this.checkedAt = checkedAt;
        this.isChecked = isChecked;
    }

    public static CheckList create(String content, Long userId, Long postId) {
        return new CheckList(null, content, userId, postId, LocalDateTime.now(), null, false);
    }

    public void update(String newContent) {
        this.content = newContent;
    }

    public void check() {
        this.isChecked = true;
        this.checkedAt = LocalDateTime.now();
    }

    public void unCheck() {
        this.isChecked = false;
        this.checkedAt = null;
    }

}
