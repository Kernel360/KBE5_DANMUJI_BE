package com.back2basics.adapter.persistence.notification;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationEntityRepository extends JpaRepository<NotificationEntity, Long> {

    List<NotificationEntity> findByClientIdAndDeletedAtIsNull(Long clientId);

    List<NotificationEntity> findByClientIdAndIsReadFalseAndDeletedAtIsNull(Long clientId);

    long countByClientIdAndIsReadFalseAndDeletedAtIsNull(Long clientId);
}