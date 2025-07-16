package com.back2basics.domain.project.dto.request;

import java.util.List;

public interface UserDistinctCheckDto {

    List<Long> devManagerId();

    List<Long> clientManagerId();

    List<Long> devUserId();

    List<Long> clientUserId();
}
