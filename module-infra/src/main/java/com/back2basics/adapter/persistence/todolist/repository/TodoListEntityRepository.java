package com.back2basics.adapter.persistence.todolist.repository;

import com.back2basics.adapter.persistence.todolist.entity.TodoListEntity;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TodoListEntityRepository extends JpaRepository<TodoListEntity, Long> {

    List<TodoListEntity> findAllByUserId(Long userId);

    @Query("SELECT t FROM TodoListEntity t WHERE t.user.id = :userId AND t.createdAt >= :start AND t.createdAt < :end")
    List<TodoListEntity> findByToday(@Param("userId") Long userId,
        @Param("start") LocalDateTime start,
        @Param("end") LocalDateTime end);
}
