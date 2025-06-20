package com.back2basics.user.port.out;

import com.back2basics.user.model.User;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserQueryPort {

    User findByUsername(String username);

    User findById(Long userId);

    List<User> findByIds(List<Long> ids);

    boolean existsByUsername(String username);

    List<User> findAll();

    Page<User> findAllByDeletedAtIsNull(Pageable pageable);

    Page<User> findAllByDeletedAtIsNotNull(Pageable pageable);

    List<User> findAllByCompanyId(Long companyId);

    List<User> findAllByCompanyIdAndDeletedAtIsNull(Long companyId);

    boolean existsById(Long userId);

    void saveAll(List<User> users);
}
