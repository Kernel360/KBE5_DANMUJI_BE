package com.back2basics.user.service.result;

public record UserSimpleResult(Long id, String username, String name, String phone, String position,
                               Long companyId, String companyName) {

}
