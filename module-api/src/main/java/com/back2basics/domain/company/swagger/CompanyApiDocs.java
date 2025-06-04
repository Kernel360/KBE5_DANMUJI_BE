package com.back2basics.domain.company.swagger;

import com.back2basics.domain.company.dto.request.CreateCompanyRequest;
import com.back2basics.domain.company.dto.request.UpdateCompanyRequest;
import com.back2basics.domain.company.dto.response.ReadCompanyResponse;
import com.back2basics.global.response.result.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "Company", description = "회사 관리 API")
public interface CompanyApiDocs {

    @Operation(summary = "회사 생성", description = "새로운 회사를 생성합니다.",
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "회사 생성 요청",
            required = true,
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = CreateCompanyRequest.class),
                examples = @ExampleObject(name = "회사 생성 예시", value = CompanyDocsResult.COMPANY_CREATE_REQUEST)
            )
        )
    )
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "201",
            description = "회사 생성 성공 (COMP201 - 회사 생성 완료)",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = ApiResponse.class),
                examples = @ExampleObject(name = "성공 응답", value = CompanyDocsResult.COMPANY_CREATE_SUCCESS))
        ),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "400",
            description = "잘못된 요청 데이터 (COMP002 - Invalid input type)",
            content = @Content(mediaType = "application/json",
                examples = @ExampleObject(value = CompanyDocsResult.INVALID_INPUT))
        )
    })
    ResponseEntity<ApiResponse<Long>> createCompany(
        @RequestBody @Valid CreateCompanyRequest request);

    @Operation(summary = "회사 단건 조회", description = "회사 ID를 통해 특정 회사를 조회합니다.")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            description = "회사 조회 성공 (COMP202 - 회사 조회 완료)",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = ApiResponse.class),
                examples = @ExampleObject(value = CompanyDocsResult.COMPANY_READ_SUCCESS))
        ),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "404",
            description = "회사를 찾을 수 없음 (COMP001 - 회사를 찾을 수 없습니다)",
            content = @Content(mediaType = "application/json",
                examples = @ExampleObject(value = CompanyDocsResult.COMPANY_NOT_FOUND))
        )
    })
    ResponseEntity<ApiResponse<ReadCompanyResponse>> getCompanyById(
        @PathVariable Long companyId);

    @Operation(summary = "회사 목록 조회 (페이징)", description = "페이징을 통해 회사 목록을 조회합니다.")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            description = "회사 목록 조회 성공 (COMP203 - 회사 목록 조회 완료)",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = ApiResponse.class),
                examples = @ExampleObject(value = CompanyDocsResult.COMPANY_READ_ALL_SUCCESS))
        )
    })
    @Parameters({
        @Parameter(name = "page", description = "페이지 번호 (0부터 시작)", example = "0"),
        @Parameter(name = "size", description = "페이지 크기 (한 페이지에 몇 개 보여줄지)", example = "10"),
        @Parameter(name = "sort", description = "정렬 기준 (예: name,asc 또는 name,desc)", example = "name,asc")
    })
    ResponseEntity<ApiResponse<Page<ReadCompanyResponse>>> getAllCompanies(
        @PageableDefault(
            page = 0,
            size = 10,
            sort = "name",
            direction = Sort.Direction.ASC
        )
        Pageable pageable);

    @Operation(summary = "키워드를 이용한 회사 목록 조회 (페이징)", description = "페이징을 통해 회사 목록을 조회합니다.")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            description = "회사 목록 조회 성공 (COMP203 - 회사 목록 조회 완료)",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = ApiResponse.class),
                examples = @ExampleObject(value = CompanyDocsResult.COMPANY_READ_BY_NAME_ALL_SUCCESS))
        )
    })
    ResponseEntity<ApiResponse<Page<ReadCompanyResponse>>> getCompanyByName(
        @PageableDefault(
            page = 0,
            size = 10,
            sort = "name",
            direction = Sort.Direction.ASC
        )
        Pageable pageable,
        @RequestParam String name);

    @Operation(summary = "회사 삭제", description = "회사 ID를 이용해 회사를 소프트 삭제 합니다.")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            description = "회사 삭제 성공 (COMP205 - 회사 삭제 완료)",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = ApiResponse.class),
                examples = @ExampleObject(value = CompanyDocsResult.COMPANY_DELETE_SUCCESS))
        ),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "404",
            description = "회사를 찾을 수 없음 (COMP001 - 회사를 찾을 수 없습니다)",
            content = @Content(mediaType = "application/json",
                examples = @ExampleObject(value = CompanyDocsResult.COMPANY_NOT_FOUND))
        )
    })
    ResponseEntity<ApiResponse<Void>> deleteCompany(@PathVariable Long companyId);

    @Operation(summary = "회사 수정", description = "회사 ID를 이용해 회사를 수정합니다.",
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "회사 수정 요청",
            required = true,
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = CreateCompanyRequest.class),
                examples = @ExampleObject(name = "회사 수정 예시", value = CompanyDocsResult.COMPANY_UPDATE_REQUEST)
            )
        )
    )
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            description = "회사 수정 성공 (COMP204 - 회사 수정 완료)",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = ApiResponse.class),
                examples = @ExampleObject(name = "성공 응답", value = CompanyDocsResult.COMPANY_UPDATE_SUCCESS))
        ),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "400",
            description = "잘못된 요청 데이터 (COMP002 - Invalid input type)",
            content = @Content(mediaType = "application/json",
                examples = @ExampleObject(value = CompanyDocsResult.INVALID_INPUT))
        ),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "404",
            description = "회사를 찾을 수 없음 (COMP001 - 회사를 찾을 수 없습니다)",
            content = @Content(mediaType = "application/json",
                examples = @ExampleObject(value = CompanyDocsResult.COMPANY_NOT_FOUND))
        )
    })
    ResponseEntity<ApiResponse<Long>> updateCompany(@PathVariable Long companyId,
        @Valid @RequestBody UpdateCompanyRequest request);
}
