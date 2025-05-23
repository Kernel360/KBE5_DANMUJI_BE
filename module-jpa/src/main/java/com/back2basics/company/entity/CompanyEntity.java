package com.back2basics.company.entity;

import com.back2basics.model.company.Company;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "company")
public class CompanyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long company_id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "ceo_name", nullable = false)
    private String ceoName;

    @Column(name = "bio", nullable = false)
    private String bio;

    @Enumerated(EnumType.STRING)
    private Company.CompanyType companyType;

    @Column(name = "biz_no", nullable = false)
    private int bizNo;

    @Column(name = "address", nullable = false)
    private String address;

    public enum CompanyType {
        CLIENT,
        AGENCY
    }

    @Builder
    public CompanyEntity(Long companyId, String name, String ceoName, String bio,
        Company.CompanyType companyType, int bizNo, String address) {
        this.company_id = companyId;
        this.name = name;
        this.ceoName = ceoName;
        this.bio = bio;
        this.companyType = companyType;
        this.bizNo = bizNo;
        this.address = address;
    }

}
