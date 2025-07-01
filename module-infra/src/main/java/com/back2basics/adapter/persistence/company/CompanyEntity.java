package com.back2basics.adapter.persistence.company;

import com.back2basics.adapter.persistence.common.entity.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "companies")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CompanyEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "ceo_name", nullable = false)
    private String ceoName;

    @Column(name = "bio", nullable = false)
    private String bio;

    @Column(name = "biz_no", nullable = false, unique = true)
    private Long bizNo;

    @Column(name = "zonecode", nullable = false)
    private String zoneCode;

    @Column(name = "address", nullable = false, unique = true)
    private String address;

    @Column(name = "email", nullable = false)
    private String email;
    @Column(name = "tel", nullable = false)
    private String tel;

    @Builder
    public CompanyEntity(Long id, String name, String ceoName, String bio,
        Long bizNo, String zoneCode, String address, String email, String tel) {
        this.id = id;
        this.name = name;
        this.ceoName = ceoName;
        this.bio = bio;
        this.bizNo = bizNo;
        this.zoneCode = zoneCode;
        this.address = address;
        this.email = email;
        this.tel = tel;
    }

}
