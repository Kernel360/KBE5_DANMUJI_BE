package com.back2basics.domain.company.dto.response;

import com.back2basics.company.service.result.CompanySummaryResult;

public record CompanySummaryResponse(Long companyId, String companyName) {
    public static CompanySummaryResponse toResponse(CompanySummaryResult result) {
        return new CompanySummaryResponse(result.id(), result.companyName());
    }
}
