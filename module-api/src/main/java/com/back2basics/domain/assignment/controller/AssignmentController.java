package com.back2basics.domain.assignment.controller;

import static com.back2basics.domain.assignment.controller.code.AssignmentResponseCode.ASSIGNMENT_COMPANY_DELETE_SUCCESS;

import com.back2basics.assignment.port.in.DeleteAssignmentUseCase;
import com.back2basics.global.response.result.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/assignments")
public class AssignmentController {

    private final DeleteAssignmentUseCase deleteAssignmentUseCase;

    //todo: 사용안하면 추후 삭제
    @DeleteMapping("/{projectId}/companies/{companyId}")
    public ResponseEntity<ApiResponse<Void>> deleteCompany(@PathVariable Long projectId,
        @PathVariable Long companyId) {
        deleteAssignmentUseCase.deleteCompanies(projectId, companyId);
        return ApiResponse.success(ASSIGNMENT_COMPANY_DELETE_SUCCESS);
    }
}
