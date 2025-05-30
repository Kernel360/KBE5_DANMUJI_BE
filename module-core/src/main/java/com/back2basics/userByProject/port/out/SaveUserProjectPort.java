package com.back2basics.userByProject.port.out;

import com.back2basics.userByProject.model.UserByProject;

public interface SaveUserProjectPort {

    void save(UserByProject userByProject);
}
