// src/main/java/com/example/todo_api/repository/UserRepository.java
package com.example.todo_api.repository;

import com.example.todo_api.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}