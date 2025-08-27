package com.example.todo_api.repository;

import com.example.todo_api.entity.TodoItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoRepository extends JpaRepository<TodoItem, Long> {
    // 自动拥有 save(), findById(), findAll(), deleteById() 等方法
}