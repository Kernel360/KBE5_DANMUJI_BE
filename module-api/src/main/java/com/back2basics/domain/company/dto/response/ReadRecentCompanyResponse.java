package com.back2basics.domain.company.dto.response;

import com.back2basics.company.service.result.ReadRecentCompanyResult;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReadRecentCompanyResponse {

    private Long id;
    private String name;
    private LocalDateTime createdAt;

    public static ReadRecentCompanyResponse toResponse(ReadRecentCompanyResult result) {
        return ReadRecentCompanyResponse.builder()
            .id(result.getId())
            .name(result.getName())
            .createdAt(result.getCreatedAt())
            .build();
    }

    public static List<ReadRecentCompanyResponse> toResponse(List<ReadRecentCompanyResult> result) {
        return result.stream()
            .map(ReadRecentCompanyResponse::toResponse)
            .collect(Collectors.toList());
    }

}
