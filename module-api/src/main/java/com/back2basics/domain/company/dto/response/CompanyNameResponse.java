package com.back2basics.domain.company.dto.response;

import com.back2basics.company.service.result.CompanyNameResult;

public record CompanyNameResponse(Long companyId, String companyName) {

    public static CompanyNameResponse from(CompanyNameResult result) {
        return new CompanyNameResponse(result.companyId(), result.companyName());
    }
}
