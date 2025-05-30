package com.back2basics.adapter.persistence.UserByProject.adapter;

import com.back2basics.userByProject.model.UserByProject;
import com.back2basics.userByProject.port.out.SaveUserProjectPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SaveUserProjectAdapter implements SaveUserProjectPort {

    @Override
    public void save(UserByProject userByProject) {

    }
}
