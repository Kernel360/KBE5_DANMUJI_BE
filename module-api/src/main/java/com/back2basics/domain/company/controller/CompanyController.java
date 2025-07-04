package com.back2basics.domain.company.controller;

import static com.back2basics.domain.company.controller.code.CompanyResponseCode.COMPANY_CREATE_SUCCESS;
import static com.back2basics.domain.company.controller.code.CompanyResponseCode.COMPANY_DELETE_SUCCESS;
import static com.back2basics.domain.company.controller.code.CompanyResponseCode.COMPANY_READ_ALL_SUCCESS;
import static com.back2basics.domain.company.controller.code.CompanyResponseCode.COMPANY_READ_SUCCESS;
import static com.back2basics.domain.company.controller.code.CompanyResponseCode.COMPANY_SEARCH_SUCCESS;
import static com.back2basics.domain.company.controller.code.CompanyResponseCode.COMPANY_UPDATE_SUCCESS;
import static com.back2basics.domain.company.controller.code.CompanyResponseCode.COMPANY_USER_LIST_SUCCESS;

import com.back2basics.company.port.in.CreateCompanyUseCase;
import com.back2basics.company.port.in.DeleteCompanyUseCase;
import com.back2basics.company.port.in.ReadCompanyUseCase;
import com.back2basics.company.port.in.UpdateCompanyUseCase;
import com.back2basics.company.service.result.ReadCompanyResult;
import com.back2basics.company.service.result.ReadRecentCompanyResult;
import com.back2basics.domain.company.dto.request.CreateCompanyRequest;
import com.back2basics.domain.company.dto.request.UpdateCompanyRequest;
import com.back2basics.domain.company.dto.response.ReadCompanyResponse;
import com.back2basics.domain.company.dto.response.ReadRecentCompanyResponse;
import com.back2basics.domain.user.dto.response.UserListResponse;
import com.back2basics.domain.user.dto.response.UserSummaryResponse;
import com.back2basics.global.response.result.ApiResponse;
import com.back2basics.security.model.CustomUserDetails;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/companies")
@RequiredArgsConstructor
public class CompanyController /*implements CompanyApiDocs*/ {

    private final CreateCompanyUseCase createCompanyUseCase;
    private final DeleteCompanyUseCase deleteCompanyUseCase;
    private final ReadCompanyUseCase readCompanyUseCase;
    private final UpdateCompanyUseCase updateCompanyUseCase;

    @PostMapping
    public ResponseEntity<ApiResponse<Long>> createCompany(
        @AuthenticationPrincipal CustomUserDetails customUserDetails,
        @RequestBody @Valid CreateCompanyRequest request) {

        Long id = createCompanyUseCase.createCompany(request.toCommand(),
            customUserDetails.getId());
        return ApiResponse.success(COMPANY_CREATE_SUCCESS, id);
    }

    @GetMapping("/recent-companies")
    public ResponseEntity<ApiResponse<List<ReadRecentCompanyResponse>>> getRecentCompanies(
    ) {
        List<ReadRecentCompanyResult> companies = readCompanyUseCase.getRecentCompanies();

        List<ReadRecentCompanyResponse> responseList = companies.stream()
            .map(ReadRecentCompanyResponse::toResponse).toList();
        return ApiResponse.success(COMPANY_READ_ALL_SUCCESS,
            responseList);
    }

    @GetMapping("/{companyId}")
    public ResponseEntity<ApiResponse<ReadCompanyResponse>> getCompanyById(
        @PathVariable Long companyId) {
        ReadCompanyResult result = readCompanyUseCase.getCompany(companyId);
        return ApiResponse.success(COMPANY_READ_SUCCESS,
            ReadCompanyResponse.toResponse(result));
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<Page<ReadCompanyResponse>>> getCompanyByName(
        @PageableDefault(
            page = 0,
            size = 10,
            sort = "createdAt",
            direction = Direction.DESC
        )
        Pageable pageable,
        @RequestParam(defaultValue = "") String name) {
        Page<ReadCompanyResult> companies = readCompanyUseCase.getCompaniesByNameContaining(
            pageable,
            name);
        return ApiResponse.success(COMPANY_SEARCH_SUCCESS,
            companies.map(ReadCompanyResponse::toResponse));
    }


    @GetMapping
    public ResponseEntity<ApiResponse<Page<ReadCompanyResponse>>> getAllCompanies(
        @PageableDefault(
            page = 0,
            size = 10,
            sort = "createdAt",
            direction = Direction.DESC
        )
        Pageable pageable) {
        Page<ReadCompanyResult> companies = readCompanyUseCase.getAllCompanies(pageable);
        return ApiResponse.success(COMPANY_READ_ALL_SUCCESS,
            companies.map(ReadCompanyResponse::toResponse));
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<ReadCompanyResponse>>> getAllCompaniesWithoutPagination() {
        List<ReadCompanyResult> companies = readCompanyUseCase.getAllCompanies();

        List<ReadCompanyResponse> responseList = companies.stream()
            .map(ReadCompanyResponse::toResponse)
            .toList();

        return ApiResponse.success(COMPANY_READ_ALL_SUCCESS, responseList);
    }

    @GetMapping("/{companyId}/users")
    public ResponseEntity<ApiResponse<List<UserSummaryResponse>>> getUsersByCompanyId(
        @PathVariable Long companyId) {
        List<UserSummaryResponse> responseList = readCompanyUseCase.getUsersByCompanyId(companyId)
            .stream()
            .map(UserSummaryResponse::from)
            .toList();
        return ApiResponse.success(COMPANY_USER_LIST_SUCCESS, responseList);
    }

    @GetMapping("/{companyId}/userLists")
    public ResponseEntity<ApiResponse<List<UserListResponse>>> getUsersInfoByCompanyId(
        @PathVariable Long companyId) {
        List<UserListResponse> responseList = readCompanyUseCase.getUsersInfoByCompanyId(companyId)
            .stream()
            .map(UserListResponse::from)
            .toList();
        return ApiResponse.success(COMPANY_USER_LIST_SUCCESS, responseList);
    }

    @PutMapping("/{companyId}")
    public ResponseEntity<ApiResponse<Long>> updateCompany(
        @AuthenticationPrincipal CustomUserDetails customUserDetails,
        @PathVariable Long companyId,
        @Valid @RequestBody UpdateCompanyRequest request) {

        updateCompanyUseCase.updateCompany(companyId, request.toCommand(),
            customUserDetails.getId());
        return ApiResponse.success(COMPANY_UPDATE_SUCCESS, companyId);
    }

    @DeleteMapping("/{companyId}")
    public ResponseEntity<ApiResponse<Void>> deleteCompany(
        @AuthenticationPrincipal CustomUserDetails customUserDetails,
        @PathVariable Long companyId) {

        deleteCompanyUseCase.deleteCompany(companyId, customUserDetails.getId());
        return ApiResponse.success(COMPANY_DELETE_SUCCESS);
    }

}
