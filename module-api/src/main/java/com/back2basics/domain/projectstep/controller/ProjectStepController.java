package com.back2basics.domain.projectstep.controller;

import com.back2basics.domain.projectstep.controller.code.ProjectStepResponseCode;
import com.back2basics.domain.projectstep.dto.request.CreateProjectStepRequest;
import com.back2basics.global.response.result.ApiResponse;
import com.back2basics.projectstep.port.in.CreateProjectStepUseCase;
import com.back2basics.projectstep.port.in.command.CreateProjectStepCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/step")
public class ProjectStepController {
    private final CreateProjectStepUseCase createProjectStepUseCase;

    // param
    @PostMapping
    public ResponseEntity<ApiResponse<Void>> createStep(@RequestBody CreateProjectStepRequest request, @RequestParam Long projectId){
        CreateProjectStepCommand command = request.toCommand();
        createProjectStepUseCase.createStep(command, projectId);
        return ApiResponse.success(ProjectStepResponseCode.STEP_CREATE_SUCCESS);
    }
}
