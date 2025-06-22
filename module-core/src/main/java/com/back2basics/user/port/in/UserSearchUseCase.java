package com.back2basics.user.port.in;

import com.back2basics.user.service.result.UserSummaryResult;
import java.util.List;

public interface UserSearchUseCase {

    List<UserSummaryResult> searchByUsername(String username);

}
