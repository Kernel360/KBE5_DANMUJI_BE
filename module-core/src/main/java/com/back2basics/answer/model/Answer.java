package com.back2basics.answer.model;

import com.back2basics.answer.port.in.command.UpdateAnswerCommand;
import com.back2basics.history.strategy.TargetDomain;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Answer implements TargetDomain {

    private Long id;
    private Long inquiryId;
    private Long authorId;
    private String content;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;
    private boolean isDelete;


    @Builder
    public Answer(Long id, Long inquiryId, Long authorId, String content,
        LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime deletedAt) {
        this.id = id;
        this.inquiryId = inquiryId;
        this.authorId = authorId;
        this.content = content;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
        this.isDelete = false;
    }

    public void update(UpdateAnswerCommand command) {
        if (command.getContent() != null) {
            this.content = command.getContent();
        }
    }

    public void markDeleted() {
        this.isDelete = true;
    }

    public static Answer copyOf(Answer source) {
        return Answer.builder()
            .id(source.getId())
            .inquiryId(source.getInquiryId())
            .authorId(source.getAuthorId())
            .content(source.getContent())
            .createdAt(source.getCreatedAt())
            .updatedAt(source.getUpdatedAt())
            .deletedAt(source.getDeletedAt())
            .build();
    }

    @Override
    public Long getId(){
        return this.id;
    }

}
