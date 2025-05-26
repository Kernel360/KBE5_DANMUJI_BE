package com.back2basics.company.controller;

import com.back2basics.port.in.company.CreateCompanyUseCase;
import com.back2basics.port.in.company.DeleteCompanyUseCase;
import com.back2basics.port.in.company.GetCompanyUseCase;
import com.back2basics.port.in.company.UpdateCompanyUseCase;
import com.back2basics.response.global.result.ApiResponse;
import com.back2basics.service.company.dto.CompanyCreateCommand;
import com.back2basics.service.company.dto.CompanyResponseDto;
import com.back2basics.service.company.dto.CompanyUpdateCommand;
import com.back2basics.service.company.response.CompanyResponseCode;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/companies")
@RequiredArgsConstructor
public class CompanyController {

    private final CreateCompanyUseCase createCompanyUseCase;
    private final DeleteCompanyUseCase deleteCompanyUseCase;
    private final GetCompanyUseCase getCompanyUseCase;
    private final UpdateCompanyUseCase updateCompanyUseCase;

    @PostMapping
    public ResponseEntity<ApiResponse<Long>> createCompany(
        @RequestBody @Valid CompanyCreateCommand command) {
        Long id = createCompanyUseCase.createCompany(command);
        return ApiResponse.success(CompanyResponseCode.COMPANY_CREATE_SUCCESS, id);
    }

    @GetMapping("/{companyId}")
    public ResponseEntity<ApiResponse<CompanyResponseDto>> getCompany(
        @PathVariable Long companyId) {
        CompanyResponseDto company = getCompanyUseCase.getCompany(companyId);
        return ApiResponse.success(CompanyResponseCode.COMPANY_READ_SUCCESS, company);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<CompanyResponseDto>>> getAllCompanies() {
        List<CompanyResponseDto> companies = getCompanyUseCase.getAllCompanies();
        return ApiResponse.success(CompanyResponseCode.COMPANY_READ_ALL_SUCCESS, companies);
    }

    @PatchMapping("/{companyId}")
    public ResponseEntity<ApiResponse<Long>> updateCompany(@PathVariable Long companyId,
        @Valid @RequestBody CompanyUpdateCommand command) {
        updateCompanyUseCase.updateCompany(companyId, command);
        return ApiResponse.success(CompanyResponseCode.COMPANY_UPDATE_SUCCESS, companyId);
    }

    @DeleteMapping("/{deleteId}/delete")
    public ResponseEntity<ApiResponse<Void>> deleteCompany(@PathVariable Long deleteId) {
        deleteCompanyUseCase.deleteCompany(deleteId);
        return ApiResponse.success(CompanyResponseCode.COMPANY_DELETE_SUCCESS);
    }

}
