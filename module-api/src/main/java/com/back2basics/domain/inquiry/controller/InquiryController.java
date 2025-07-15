package com.back2basics.domain.inquiry.controller;

import static com.back2basics.domain.inquiry.controller.code.InquiryResponseCode.INQUIRY_CREATE_SUCCESS;
import static com.back2basics.domain.inquiry.controller.code.InquiryResponseCode.INQUIRY_DELETE_SUCCESS;
import static com.back2basics.domain.inquiry.controller.code.InquiryResponseCode.INQUIRY_READ_ALL_SUCCESS;
import static com.back2basics.domain.inquiry.controller.code.InquiryResponseCode.INQUIRY_READ_SUCCESS;
import static com.back2basics.domain.inquiry.controller.code.InquiryResponseCode.INQUIRY_SEARCH_SUCCESS;
import static com.back2basics.domain.inquiry.controller.code.InquiryResponseCode.INQUIRY_UPDATE_SUCCESS;

import com.back2basics.domain.inquiry.dto.request.CreateInquiryRequest;
import com.back2basics.domain.inquiry.dto.request.SearchInquiryRequest;
import com.back2basics.domain.inquiry.dto.request.UpdateInquiryByUserRequest;
import com.back2basics.domain.inquiry.dto.request.UpdateInquiryStatusByAdminRequest;
import com.back2basics.domain.inquiry.dto.response.CountInquiryResponse;
import com.back2basics.domain.inquiry.dto.response.InquirySummaryResponse;
import com.back2basics.domain.inquiry.dto.response.ReadInquiryResponse;
import com.back2basics.domain.inquiry.dto.response.ReadRecentInquiryResponse;
import com.back2basics.global.response.result.ApiResponse;
import com.back2basics.inquiry.port.in.CreateInquiryUseCase;
import com.back2basics.inquiry.port.in.DeleteInquiryUseCase;
import com.back2basics.inquiry.port.in.ReadInquiryUseCase;
import com.back2basics.inquiry.port.in.SearchInquiryUseCase;
import com.back2basics.inquiry.port.in.UpdateInquiryUseCase;
import com.back2basics.inquiry.service.result.CountInquiryResult;
import com.back2basics.inquiry.service.result.InquirySummaryResult;
import com.back2basics.inquiry.service.result.ReadInquiryResult;
import com.back2basics.inquiry.service.result.ReadRecentInquiryResult;
import com.back2basics.security.model.CustomUserDetails;
import com.back2basics.user.model.Role;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/inquiries")
@RequiredArgsConstructor
public class InquiryController {

    private final CreateInquiryUseCase createinquiryUseCase;
    private final ReadInquiryUseCase readInquiryUseCase;
    private final DeleteInquiryUseCase deleteInquiryUseCase;
    private final UpdateInquiryUseCase updateInquiryUseCase;
    private final SearchInquiryUseCase searchInquiryUseCase;

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ApiResponse<Long>> createInquiry(
        @RequestBody @Valid CreateInquiryRequest request,
        @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        Long id = createinquiryUseCase.createInquiry(request.toCommand(),
            customUserDetails.getId());
        return ApiResponse.success(INQUIRY_CREATE_SUCCESS, id);
    }

    @GetMapping("/{inquiryId}")
    public ResponseEntity<ApiResponse<ReadInquiryResponse>> getInquiryById(
        @PathVariable Long inquiryId,
        @AuthenticationPrincipal CustomUserDetails userDetails) {

        if (userDetails.getRole() == Role.ADMIN) {
            // 관리자: userId 비교 없이 전체 조회
            ReadInquiryResult result = readInquiryUseCase.getInquiryAsAdmin(inquiryId);
            return ApiResponse.success(INQUIRY_READ_SUCCESS,
                ReadInquiryResponse.toResponse(result));
        }

        ReadInquiryResult result = readInquiryUseCase.getInquiry(inquiryId,
            userDetails.getId());
        return ApiResponse.success(INQUIRY_READ_SUCCESS,
            ReadInquiryResponse.toResponse(result));
    }

    @GetMapping("/counts")
    public ResponseEntity<ApiResponse<CountInquiryResponse>> getInquiryCounts() {
        CountInquiryResult counts = readInquiryUseCase.getInquiryCounts();
        return ApiResponse.success(INQUIRY_READ_ALL_SUCCESS,
            CountInquiryResponse.toResponse(counts));
    }

    @GetMapping("/filtering")
    public ResponseEntity<ApiResponse<Page<ReadInquiryResponse>>> getInquiryFiltered(
        @Valid @ModelAttribute SearchInquiryRequest request,
        @PageableDefault(
            page = 0,
            size = 10,
            sort = "createdAt",
            direction = Sort.Direction.DESC
        )
        Pageable pageable) {

        Page<ReadInquiryResult> inquiries = readInquiryUseCase.searchInquiries(request.toCommand(),
            pageable);
        return ApiResponse.success(INQUIRY_READ_ALL_SUCCESS,
            inquiries.map(ReadInquiryResponse::toResponse));
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<Page<InquirySummaryResponse>>> searchPosts(
        @AuthenticationPrincipal CustomUserDetails customUserDetails,
        @Valid @ModelAttribute SearchInquiryRequest request,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<InquirySummaryResult> result = searchInquiryUseCase.searchWithFilter(request.toCommand(), pageable);
        Page<InquirySummaryResponse> response = result.map(
            InquirySummaryResponse::toResponse);

        return ApiResponse.success(INQUIRY_SEARCH_SUCCESS, response);
    }

    @GetMapping("/my/filtering")
    public ResponseEntity<ApiResponse<Page<ReadInquiryResponse>>> getMyInquiryFiltered(
        @Valid @ModelAttribute SearchInquiryRequest request,
        @AuthenticationPrincipal CustomUserDetails userDetails,
        @PageableDefault(
            page = 0,
            size = 10,
            sort = "createdAt",
            direction = Sort.Direction.DESC
        )
        Pageable pageable) {

        Page<ReadInquiryResult> inquiries = readInquiryUseCase.searchUserInquiries(
            userDetails.getId(), request.toCommand(),
            pageable);
        return ApiResponse.success(INQUIRY_READ_ALL_SUCCESS,
            inquiries.map(ReadInquiryResponse::toResponse));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Page<ReadInquiryResponse>>> getAllInquiries(
        @PageableDefault(
            page = 0,
            size = 10,
            sort = "createdAt",
            direction = Sort.Direction.DESC
        )
        Pageable pageable) {
        Page<ReadInquiryResult> inquiries = readInquiryUseCase.getAllInquiries(pageable);
        return ApiResponse.success(INQUIRY_READ_ALL_SUCCESS,
            inquiries.map(ReadInquiryResponse::toResponse));
    }

    @GetMapping("/my")
    public ResponseEntity<ApiResponse<Page<ReadInquiryResponse>>> getMyInquiries(
        @PageableDefault(
            page = 0,
            size = 10,
            sort = "id",
            direction = Sort.Direction.DESC
        )
        Pageable pageable,
        @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        Page<ReadInquiryResult> inquires = readInquiryUseCase.getMyInquiries(pageable,
            customUserDetails.getId());
        return ApiResponse.success(INQUIRY_READ_ALL_SUCCESS,
            inquires.map(ReadInquiryResponse::toResponse));
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<ReadInquiryResponse>>> getAllInquiriesWithoutPagination() {
        List<ReadInquiryResult> inquiries = readInquiryUseCase.getAllInquiries();

        List<ReadInquiryResponse> responseList = inquiries.stream()
            .map(ReadInquiryResponse::toResponse)
            .toList();

        return ApiResponse.success(INQUIRY_READ_ALL_SUCCESS, responseList);
    }

    @GetMapping("/recent-inquiries")
    public ResponseEntity<ApiResponse<List<ReadRecentInquiryResponse>>> getRecentCompanies(
    ) {
        List<ReadRecentInquiryResult> inquiries = readInquiryUseCase.getRecentInquiries();

        List<ReadRecentInquiryResponse> responseList = inquiries.stream()
            .map(ReadRecentInquiryResponse::toResponse).toList();
        return ApiResponse.success(INQUIRY_READ_ALL_SUCCESS,
            responseList);
    }

    // todo:  의견
    // swlee: 사실 여기서 권한체크를 하면 편하긴 한데 서비스레이어에서 validator 통해서 검증로직을 처리하므로
    // validator에게 권한 검증 로직을 위임하는 것이 나아보입니다.
    // byuser 메소드는 시큐리티객체 사용해야ㅊ되고 byadmin은 사용안해도 되는 것도 비일관적입니다.(byadmin은 이력생성때문에 제가 넣었어요)
    @PutMapping("/{inquiryId}/user")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ApiResponse<Void>> updateByUser(
        @PathVariable Long inquiryId,
        @RequestBody UpdateInquiryByUserRequest request,
        @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        updateInquiryUseCase.updateByUser(inquiryId, userDetails.getId(), request.toCommand());
        return ApiResponse.success(INQUIRY_UPDATE_SUCCESS);
    }

    @PutMapping("/{inquiryId}/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> updateByAdmin(
        @AuthenticationPrincipal CustomUserDetails customUserDetails,
        @PathVariable Long inquiryId,
        @RequestBody UpdateInquiryStatusByAdminRequest request
    ) {
        updateInquiryUseCase.updateByAdmin(inquiryId, request.toCommand(),
            customUserDetails.getId());
        return ApiResponse.success(INQUIRY_UPDATE_SUCCESS);
    }

    @DeleteMapping("/{inquiryId}")
    public ResponseEntity<ApiResponse<Void>> deleteInquiry(@PathVariable Long inquiryId,
        @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        deleteInquiryUseCase.deleteInquiry(inquiryId, customUserDetails.getId());
        return ApiResponse.success(INQUIRY_DELETE_SUCCESS);
    }

}
