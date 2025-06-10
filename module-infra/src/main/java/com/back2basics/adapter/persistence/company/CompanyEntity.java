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
@Table(name = "company")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CompanyEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "ceo_name", nullable = false)
    private String ceoName;

    @Column(name = "bio", nullable = false)
    private String bio;

    @Column(name = "biz_no", nullable = false)
    private Long bizNo;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "email", nullable = false)
    private String email;
    @Column(name = "tel", nullable = false)
    private String tel;

    @Builder
    public CompanyEntity(Long id, String name, String ceoName, String bio,
        Long bizNo, String address, String email, String tel) {
        this.id = id;
        this.name = name;
        this.ceoName = ceoName;
        this.bio = bio;
        this.bizNo = bizNo;
        this.address = address;
        this.email = email;
        this.tel = tel;
    }

}
