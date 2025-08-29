// src/main/java/com/example/todo_api/dto/TaskDto.java
package com.example.todo_api.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskDto {
    private Long id;
    private String title;
    private String description;
    private String status;
    private LocalDateTime createdAt;
    private Long userId;
}