package com.back2basics.adapter.persistence.notification;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationEntityRepository extends JpaRepository<NotificationEntity, Long> {

    Page<NotificationEntity> findByClientIdAndDeletedAtIsNull(Long clientId, Pageable pageable);

    List<NotificationEntity> findByClientIdAndIsReadFalseAndDeletedAtIsNull(Long clientId);

    long countByClientIdAndIsReadFalseAndDeletedAtIsNull(Long clientId);
}