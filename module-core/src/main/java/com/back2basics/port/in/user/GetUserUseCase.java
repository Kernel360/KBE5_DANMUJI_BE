package com.back2basics.port.in.user;

import com.back2basics.service.user.dto.UserResponseDto;

public interface GetUserUseCase {

    UserResponseDto getUser(Long userId);
}
