package com.back2basics.company.controller;

import com.back2basics.port.in.company.CreateCompanyUseCase;
import com.back2basics.port.in.company.DeleteCompanyUseCase;
import com.back2basics.response.company.CompanyResponseCode;
import com.back2basics.response.global.result.ResultResponse;
import com.back2basics.service.company.dto.CompanyCreateCommand;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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

    @PostMapping
    public ResponseEntity<ResultResponse> createCompany(
        @RequestBody @Valid CompanyCreateCommand command) {
        Long id = createCompanyUseCase.createCompany(command);
        return ResponseEntity.ok(ResultResponse.of(CompanyResponseCode.COMPANY_CREATE_SUCCESS, id));
    }
 
    @DeleteMapping("/{id}")
    public ResponseEntity<ResultResponse> deleteCompany(@PathVariable Long id) {
        deleteCompanyUseCase.deleteCompany(id);
        return ResponseEntity.ok(
            ResultResponse.of(CompanyResponseCode.COMPANY_DELETE_SUCCESS, null));
    }
}
