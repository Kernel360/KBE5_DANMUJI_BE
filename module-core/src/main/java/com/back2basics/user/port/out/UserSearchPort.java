package com.back2basics.user.port.out;

import com.back2basics.user.model.User;
import java.util.List;

public interface UserSearchPort {

    List<User> searchByUsernameAndProjectId(String username, Long projectId);
}
