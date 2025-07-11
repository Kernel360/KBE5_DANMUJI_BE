package com.back2basics.domain.company.dto.response;

import com.back2basics.company.service.result.ReadCompanyResult;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReadCompanyResponse {

    private Long id;
    private String name;
    private String ceoName;
    private String bio;
    private Long bizNo;
    private String zonecode;
    private String address;
    private String email;
    private String tel;
    private LocalDateTime createdAt;
    private int userCount;

    public static ReadCompanyResponse toResponse(ReadCompanyResult result) {
        return ReadCompanyResponse.builder()
            .id(result.getId())
            .name(result.getName())
            .ceoName(result.getCeoName())
            .bio(result.getBio())
            .bizNo(result.getBizNo())
            .zonecode(result.getZonecode())
            .address(result.getAddress())
            .email(result.getEmail())
            .tel(result.getTel())
            .createdAt(result.getCreatedAt())
            .userCount(result.getUserCount())
            .build();
    }

    public static List<ReadCompanyResponse> toResponse(List<ReadCompanyResult> result) {
        return result.stream()
            .map(ReadCompanyResponse::toResponse)
            .collect(Collectors.toList());
    }

}
