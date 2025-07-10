package com.back2basics.company.service.result;

public record CompanyNameResult(Long companyId, String companyName) {

    public static CompanyNameResult toResult(Long companyId, String companyName) {
        return new CompanyNameResult(companyId, companyName);
    }
}
