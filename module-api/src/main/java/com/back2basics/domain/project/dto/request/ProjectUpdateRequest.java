package com.back2basics.domain.project.dto.request;

import com.back2basics.custom.CustomStringLengthCheck;
import com.back2basics.custom.CustomUserDistinctCheck;
import com.back2basics.custom.ManagerIncludedInUserCheck;
import com.back2basics.project.port.in.command.ProjectUpdateCommand;
import jakarta.validation.constraints.NotEmpty;
import java.time.LocalDate;
import java.util.List;

@CustomUserDistinctCheck
@ManagerIncludedInUserCheck
public record ProjectUpdateRequest(
    @CustomStringLengthCheck(min = 1, max = 50, message = "프로젝트명은 50자 이내로 입력해주세요.")
    String name,
    @CustomStringLengthCheck(min = 1, max = 255, message = "프로젝트 개요는 255자 이내로 입력해주세요.")
    String description,
    String projectCost,
    LocalDate startDate,
    LocalDate endDate,
    List<Long> devManagerId,
    List<Long> clientManagerId,
    @NotEmpty(message = "하나 이상의 개발사가 선택되어야 합니다.")
    List<Long> devUserId,
    @NotEmpty(message = "하나 이상의 고객사가 선택되어야 합니다.")
    List<Long> clientUserId)
    implements UserDistinctCheckDto {

    public ProjectUpdateCommand toCommand() {
        return ProjectUpdateCommand.builder()
            .name(name)
            .description(description)
            .projectCost(projectCost)
            .startDate(startDate)
            .endDate(endDate)
            .devManagerId(devManagerId)
            .clientManagerId(clientManagerId)
            .devUserId(devUserId)
            .clientUserId(clientUserId)
            .build();
    }
}
