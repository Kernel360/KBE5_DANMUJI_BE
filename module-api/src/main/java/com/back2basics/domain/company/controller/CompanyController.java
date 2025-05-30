package com.back2basics.domain.company.controller;

import com.back2basics.company.port.in.CreateCompanyUseCase;
import com.back2basics.company.port.in.DeleteCompanyUseCase;
import com.back2basics.company.port.in.ReadCompanyUseCase;
import com.back2basics.company.port.in.UpdateCompanyUseCase;
import com.back2basics.company.service.result.ReadCompanyResult;
import com.back2basics.domain.company.controller.code.CompanyResponseCode;
import com.back2basics.domain.company.dto.request.CreateCompanyRequest;
import com.back2basics.domain.company.dto.request.UpdateCompanyRequest;
import com.back2basics.domain.company.dto.response.ReadCompanyResponse;
import com.back2basics.global.response.result.ApiResponse;
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
    private final ReadCompanyUseCase readCompanyUseCase;
    private final UpdateCompanyUseCase updateCompanyUseCase;

    @PostMapping
    public ResponseEntity<ApiResponse<Long>> createCompany(
        @RequestBody @Valid CreateCompanyRequest request) {
        Long id = createCompanyUseCase.createCompany(request.toCommand());
        return ApiResponse.success(CompanyResponseCode.COMPANY_CREATE_SUCCESS, id);
    }

    @GetMapping("/{companyId}")
    public ResponseEntity<ApiResponse<ReadCompanyResponse>> getCompanyById(
        @PathVariable Long companyId) {
        ReadCompanyResult result = readCompanyUseCase.getCompany(companyId);
        return ApiResponse.success(CompanyResponseCode.COMPANY_READ_SUCCESS,
            ReadCompanyResponse.toResponse(result));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ReadCompanyResponse>>> getAllCompanies() {
        List<ReadCompanyResult> companies = readCompanyUseCase.getAllCompanies();
        return ApiResponse.success(CompanyResponseCode.COMPANY_READ_ALL_SUCCESS,
            ReadCompanyResponse.toResponse(companies));
    }

    @PatchMapping("/{companyId}")
    public ResponseEntity<ApiResponse<Long>> updateCompany(@PathVariable Long companyId,
        @Valid @RequestBody UpdateCompanyRequest request) {
        updateCompanyUseCase.updateCompany(companyId, request.toCommand());
        return ApiResponse.success(CompanyResponseCode.COMPANY_UPDATE_SUCCESS, companyId);
    }

    @DeleteMapping("/{companyId}")
    public ResponseEntity<ApiResponse<Void>> deleteCompany(@PathVariable Long companyId) {
        deleteCompanyUseCase.deleteCompany(companyId);
        return ApiResponse.success(CompanyResponseCode.COMPANY_DELETE_SUCCESS);
    }

}
