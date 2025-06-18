package com.back2basics.domain.inquiry.controller;

import com.back2basics.inquiry.port.in.CreateInquiryUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/inquiries")
@RequiredArgsConstructor
public class InquiryController {

    private final CreateInquiryUseCase createinquiryUseCase;

//    @PostMapping
//    public ResponseEntity<ApiResponse<Long>> createInquiry(
//        @RequestBody @Valid CreateInquiryRequest request) {
//        Long id = createinquiryUseCase.createCompany(request.toCommand());
//        return ApiResponse.success(COMPANY_CREATE_SUCCESS, id);
//    }

}
