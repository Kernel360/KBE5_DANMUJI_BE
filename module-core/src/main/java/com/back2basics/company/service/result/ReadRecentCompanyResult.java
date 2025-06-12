package com.back2basics.company.service.result;

import com.back2basics.company.model.Company;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ReadRecentCompanyResult {

    private final Long id;
    private final String name;
    private final LocalDateTime createdAt;

    @Builder
    public ReadRecentCompanyResult(Long id, String name, LocalDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.createdAt = createdAt;
    }

    public static ReadRecentCompanyResult toResult(Company company) {
        return ReadRecentCompanyResult.builder()
            .id(company.getId())
            .name(company.getName())
            .createdAt(company.getCreatedAt())
            .build();
    }
}
