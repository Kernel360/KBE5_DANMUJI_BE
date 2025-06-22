package com.back2basics.user.port.out;

import java.util.List;

public interface UserSearchPort {

    List<String> searchByUsername(String username);
}
