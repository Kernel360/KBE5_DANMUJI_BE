package com.back2basics.domain.inquiry.controller;

import static com.back2basics.domain.inquiry.controller.code.InquiryResponseCode.INQUIRY_CREATE_SUCCESS;

import com.back2basics.domain.inquiry.dto.request.CreateInquiryRequest;
import com.back2basics.global.response.result.ApiResponse;
import com.back2basics.inquiry.port.in.CreateInquiryUseCase;
import com.back2basics.security.model.CustomUserDetails;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/inquiries")
@RequiredArgsConstructor
public class InquiryController {

    private final CreateInquiryUseCase createinquiryUseCase;

    @PostMapping
    public ResponseEntity<ApiResponse<Long>> createInquiry(
        @RequestBody @Valid CreateInquiryRequest request,
        @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        Long id = createinquiryUseCase.createInquiry(request.toCommand(),
            customUserDetails.getId());
        return ApiResponse.success(INQUIRY_CREATE_SUCCESS, id);
    }

}
