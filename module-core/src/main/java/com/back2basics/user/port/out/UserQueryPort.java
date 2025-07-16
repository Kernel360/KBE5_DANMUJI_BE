package com.back2basics.user.port.out;

import com.back2basics.user.model.User;
import com.back2basics.user.port.in.command.SearchUserCommand;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserQueryPort {

    User findAdminByRole();

    User findByUsername(String username);

    User findById(Long userId);

    Optional<Long> findIdByName(String name);

    List<User> findByIds(List<Long> ids);

    boolean existsByUsername(String username);

    List<User> findAll();

    List<String> findAllPositions();

    List<User> findAllByDeletedAtIsNull();

    Page<User> findAllByDeletedAtIsNull(Pageable pageable);

    Page<User> findAllByDeletedAtIsNotNull(Pageable pageable);

    List<User> findAllByCompanyId(Long companyId);

    Page<User> findAllByCompanyId(Long companyId, Pageable pageable);

    List<User> findAllByCompanyIdAndDeletedAtIsNull(Long companyId);

    boolean existsById(Long userId);

    void saveAll(List<User> users);

    List<User> findAllByIds(List<Long> userIds);

    Map<String, Long> findUserIdsByUsernames(List<String> usernames);

    Long getUserCounts();

    Page<User> searchUsers(SearchUserCommand command, Pageable pageable);

    Map<Long, String> getNameByIds(List<Long> userIds);
}
