package com.back2basics.company.model;

import com.back2basics.company.port.in.command.UpdateCompanyCommand;
import com.back2basics.history.strategy.TargetDomain;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Company implements TargetDomain {

    private final Long id;
    private String name;
    private String ceoName;
    private String bio;
    private Long bizNo;
    private String zonecode;
    private String address;
    private String email;
    private String tel;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;
    private boolean isDelete;

    @Builder
    public Company(Long id, String name, String ceoName, String bio,
        Long bizNo, String zonecode, String address, String email, String tel,
        LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime deletedAt) {
        this.id = id;
        this.name = name;
        this.ceoName = ceoName;
        this.bio = bio;
        this.bizNo = bizNo;
        this.zonecode = zonecode;
        this.address = address;
        this.email = email;
        this.tel = tel;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
        this.isDelete = false;
    }

    public void update(UpdateCompanyCommand command) {
        this.name = command.getName();
        this.ceoName = command.getCeoName();
        this.bio = command.getBio();
        this.bizNo = command.getBizNo();
        this.zonecode = command.getZonecode();
        this.address = command.getAddress();
        this.email = command.getEmail();
        this.tel = command.getTel();
    }

    public void markDeleted() {
        this.isDelete = true;
    }

    public static Company copyOf(Company company) {
        return Company.builder()
            .id(company.getId())
            .name(company.getName())
            .ceoName(company.getCeoName())
            .bio(company.getBio())
            .bizNo(company.getBizNo())
            .zonecode(company.getZonecode())
            .address(company.getAddress())
            .email(company.getEmail())
            .tel(company.getTel())
            .createdAt(company.getCreatedAt())
            .updatedAt(company.getUpdatedAt())
            .deletedAt(company.getDeletedAt())
            .build();
    }

    @Override
    public Long getId() {
        return this.id;
    }
}
