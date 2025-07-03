package com.back2basics.project.port.in.command;

import com.back2basics.project.model.ProjectStatus;
import java.time.LocalDate;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProjectSearchCommand {
    private String keyword;
    private String category;
    private ProjectStatus projectStatus;
    private LocalDate startDate;
    private LocalDate endDate;
    private String sort;
}
