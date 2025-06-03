package com.back2basics.user.service.result;

import com.back2basics.user.model.UserType;
import java.time.LocalDateTime;

public record UserInfoResult(Long id, String username, String name, String email, String phone,
                             String position, UserType userType, Long companyId,
                             LocalDateTime CreatedAt, LocalDateTime updatedAt) {

}
