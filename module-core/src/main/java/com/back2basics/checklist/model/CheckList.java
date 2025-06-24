package com.back2basics.checklist.model;

import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class CheckList {

    private final Long id;
    private String content;
    private final Long userId;
    private final Long postId;
    private LocalDateTime checkedAt;
    private Boolean isChecked;
    private Boolean isDeleted;

    public CheckList(Long id, String content, Long userId, Long postId, LocalDateTime checkedAt,
        Boolean isChecked, Boolean isDeleted) {
        this.id = id;
        this.content = content;
        this.userId = userId;
        this.postId = postId;
        this.checkedAt = checkedAt;
        this.isChecked = isChecked;
        this.isDeleted = isDeleted;
    }

    public static CheckList create(String content, Long userId, Long postId) {
        return new CheckList(null, content, userId, postId, null, false, false);
    }

    public void delete() {
        this.isDeleted = true;
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
