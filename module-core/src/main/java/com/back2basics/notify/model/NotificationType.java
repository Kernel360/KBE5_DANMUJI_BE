package com.back2basics.notify.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum NotificationType {
    PROJECT("프로젝트"),
    POST("게시글"),
    COMMENT("댓글"),
    QUESTION("질문"),
    ANSWER("답변"),

    PROJECT_CREATE_ASSIGNMENT("새 프로젝트에 배정되었습니다."),

    PROJECT_POST_CREATED("프로젝트에 새로운 게시글이 등록되었습니다."),
    POST_REPLY_CREATED("내 게시글에 답글이 달렸습니다."),
    POST_RESTORED("비활성화 게시글이 복구되었습니다."),

    COMMENT_POST_CREATED("내 게시글에 댓글이 달렸습니다."),
    COMMENT_REPLY_CREATED("내 댓글에 답글이 달렸습니다."),

    MENTIONED("회원님이 언급되었습니다."),

    CHECKLIST_REQUEST("체크 리스트 승인 요청이 도착했습니다."),
    CHECKLIST_ACCEPTED("체크 리스트 승인이 완료되었습니다."),
    CHECKLIST_REJECTED("체크 리스트 승인이 거절되었습니다.");

    private final String description;
}
