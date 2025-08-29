// src/main/java/com/example/todo_api/repository/TaskRepository.java
package com.example.todo_api.repository;

import com.example.todo_api.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByUserId(Long userId);

    // ✅ 添加这个方法声明
    Optional<Task> findByIdAndUserId(Long id, Long userId);

    // ✅ 添加 exists 方法
    boolean existsByIdAndUserId(Long id, Long userId);
}