package com.back2basics.project.model;

import com.back2basics.service.project.dto.ProjectUpdateCommand;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

// todo : enum status 추가
@Getter
public class Project {

    private final Long id;

    private String name;

    private String description;

    private LocalDate startDate;

    private LocalDate endDate;

    private final LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private LocalDateTime deletedAt;

    private boolean isDeleted;

    @Builder
    public Project(Long id, String name, String description, LocalDate startDate, LocalDate endDate,
        LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime deletedAt, boolean isDeleted) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
        this.isDeleted = isDeleted;
    }

    public void update(ProjectUpdateCommand command) {
        if (command.getName() != null) {
            this.name = command.getName();
        }
        if (command.getDescription() != null) {
            this.description = command.getDescription();
        }
        if (command.getStartDate() != null) {
            this.startDate = command.getStartDate();
        }
        if (command.getEndDate() != null) {
            this.endDate = command.getEndDate();
        }
    }

    // 삭제를 더 직관적이게 나타내기 위헤 isDeleted 추가
//    public void softDeleted() {
//        this.isDeleted = true; // 만약 도메인에서 localDatetime을 변경한다면 entity에도 isDeleted 추가하는거 어떨지..
//        this.deletedAt = LocalDateTime.now();
//    }
}
