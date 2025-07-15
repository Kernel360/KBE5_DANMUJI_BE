package com.back2basics.inquiry.model;

import com.back2basics.history.strategy.TargetDomain;
import com.back2basics.inquiry.port.in.command.UpdateInquiryCommand;
import com.back2basics.inquiry.port.in.command.UpdateInquiryStatusCommand;
import com.back2basics.user.model.Role;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Inquiry implements TargetDomain {

    private final Long id;
    private Long authorId;
    private String name;
    private String username;
    private Role role;
    private String title;
    private String content;
    private InquiryStatus inquiryStatus;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;
    private boolean isDelete;

    @Builder
    public Inquiry(Long id, Long authorId, String name, String username, Role role, String title, String content,
        InquiryStatus inquiryStatus,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        LocalDateTime deletedAt) {
        this.id = id;
        this.authorId = authorId;
        this.name = name;
        this.username = username;
        this.role = role;
        this.title = title;
        this.content = content;
        this.inquiryStatus = inquiryStatus != null ? inquiryStatus : InquiryStatus.WAITING;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
        this.isDelete = false;
    }

    public void updateContent(UpdateInquiryCommand command) {
        if (command.getTitle() != null) {
            this.title = command.getTitle();
        }
        if (command.getContent() != null) {
            this.content = command.getContent();
        }
    }

    public void updateStatus(UpdateInquiryStatusCommand command) {
        if (command.getStatus() != null) {
            this.inquiryStatus = command.getStatus();
        }
    }

    public void markDeleted() {
        this.isDelete = true;
    }

    @Override
    public Long getId() {
        return id;
    }

    public static Inquiry copyOf(Inquiry original) {
        return Inquiry.builder()
            .id(original.getId())
            .authorId(original.getAuthorId())
            .name(original.getName())
            .username(original.getUsername())
            .role(original.getRole())
            .title(original.getTitle())
            .content(original.getContent())
            .inquiryStatus(original.getInquiryStatus())
            .createdAt(original.getCreatedAt())
            .updatedAt(original.getUpdatedAt())
            .deletedAt(original.getDeletedAt())
            .build();
    }

}
