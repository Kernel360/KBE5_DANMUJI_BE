package com.back2basics.port.out.user;

import com.back2basics.model.user.User;
import java.util.Optional;

public interface UserRepositoryPort {

	void save(User user);

	Optional<User> findById(Long userId);

}
