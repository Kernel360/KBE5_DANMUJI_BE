package com.back2basics.adapter.persistence.company.dto;

import java.time.LocalDateTime;

public interface CompanyWithUserCountProjection {

    Long getId();

    String getName();

    String getCeoName();

    String getBio();

    Long getBizNo();

    String getZonecode();

    String getAddress();

    String getEmail();

    String getTel();

    LocalDateTime getCreatedAt();

    int getUserCount();
}