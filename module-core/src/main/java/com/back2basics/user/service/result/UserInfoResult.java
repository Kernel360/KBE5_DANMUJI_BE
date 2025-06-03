package com.back2basics.user.service.result;

public record UserInfoResult(Long id, String username, String name, String email, String phone,
                             String position, Long companyId) {
 
}
