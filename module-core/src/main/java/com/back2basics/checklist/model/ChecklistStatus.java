package com.back2basics.checklist.model;

public enum ChecklistStatus {
    PENDING,        // 아직 유저들에게 요청 중
    PARTIALLY_APPROVED, // 일부 유저만 승인
    APPROVED,       // 전체 유저 승인 완료
    REJECTED,       // 유저 중 한 명이라도 거부 시
    CANCELLED       // 요청 취소됨
}