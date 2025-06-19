package com.back2basics.domain.inquiry.controller;

import static com.back2basics.domain.inquiry.controller.code.InquiryResponseCode.INQUIRY_CREATE_SUCCESS;
import static com.back2basics.domain.inquiry.controller.code.InquiryResponseCode.INQUIRY_READ_ALL_SUCCESS;
import static com.back2basics.domain.inquiry.controller.code.InquiryResponseCode.INQUIRY_READ_SUCCESS;

import com.back2basics.domain.inquiry.dto.request.CreateInquiryRequest;
import com.back2basics.domain.inquiry.dto.response.ReadInquiryResponse;
import com.back2basics.global.response.result.ApiResponse;
import com.back2basics.inquiry.port.in.CreateInquiryUseCase;
import com.back2basics.inquiry.port.in.ReadInquiryUseCase;
import com.back2basics.inquiry.service.result.ReadInquiryResult;
import com.back2basics.security.model.CustomUserDetails;
import com.back2basics.user.port.in.UserQueryUseCase;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/inquiries")
@RequiredArgsConstructor
public class InquiryController {

    private final CreateInquiryUseCase createinquiryUseCase;
    private final ReadInquiryUseCase readInquiryUseCase;
    private final UserQueryUseCase userQueryUseCase;

    @PostMapping
    public ResponseEntity<ApiResponse<Long>> createInquiry(
        @RequestBody @Valid CreateInquiryRequest request,
        @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        Long id = createinquiryUseCase.createInquiry(request.toCommand(),
            customUserDetails.getId());
        return ApiResponse.success(INQUIRY_CREATE_SUCCESS, id);
    }

    @GetMapping("/{inquiryId}")
    public ResponseEntity<ApiResponse<ReadInquiryResponse>> getInquiryById(
        @PathVariable Long inquiryId) {
        ReadInquiryResult result = readInquiryUseCase.getInquiry(inquiryId);
        return ApiResponse.success(INQUIRY_READ_SUCCESS,
            ReadInquiryResponse.toResponse(result));
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<ReadInquiryResponse>>> getAllInquiriesWithoutPagination() {
        List<ReadInquiryResult> inquiries = readInquiryUseCase.getAllInquiries();

        List<ReadInquiryResponse> responseList = inquiries.stream()
            .map(ReadInquiryResponse::toResponse)
            .toList();

        return ApiResponse.success(INQUIRY_READ_ALL_SUCCESS, responseList);
    }

//    @DeleteMapping("/{inquiryId}")
//    public ResponseEntity<ApiResponse<Void>> deleteInquiry(@PathVariable Long inquiryId) {
//        deleteinquiryUseCase.deleteCompany(inquiryId);
//        return ApiResponse.success(COMPANY_DELETE_SUCCESS);
//    }

}
