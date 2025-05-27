package com.back2basics.model.project;

import com.back2basics.service.project.dto.ProjectUpdateCommand;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import lombok.Builder;
import lombok.Getter;
import org.springframework.cglib.core.Local;

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

    @Builder
    public Project(Long id, String name, String description, LocalDate startDate, LocalDate endDate,
        LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime deletedAt) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
    }

    public void update(ProjectUpdateCommand command) {
        if(command.getName() != null) {
            this.name = command.getName();
        }
        if(command.getDescription() != null) {
            this.description = command.getDescription();
        }
        if(command.getStartDate() != null) {
            this.startDate = command.getStartDate();
        }
        if(command.getEndDate() != null) {
            this.endDate = command.getEndDate();
        }
    }
}
