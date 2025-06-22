package com.back2basics.user.port.in;

import java.util.List;

public interface UserSearchUseCase {

    List<String> searchByUsername(String username);

}
