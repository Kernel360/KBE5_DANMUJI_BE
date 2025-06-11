package com.back2basics.adapter.persistence.notification;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationEntityRepository extends JpaRepository<NotificationEntity, Long> {

    List<NotificationEntity> findByClientId(Long clientId);

    List<NotificationEntity> findByClientIdAndIsReadFalse(Long clientId);

    long countByClientIdAndIsReadFalse(Long clientId);
}