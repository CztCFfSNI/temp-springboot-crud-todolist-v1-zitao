// src/main/java/com/example/todo_api/controller/TaskController.java
package com.example.todo_api.controller;

import com.example.todo_api.dto.TaskDto;
import com.example.todo_api.entity.Task;
import com.example.todo_api.entity.User;
import com.example.todo_api.entity.Status;
import com.example.todo_api.repository.TaskRepository;
import com.example.todo_api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/tasks")
@CrossOrigin(origins = "*") // 允许前端访问（开发用）
public class TaskController {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    // 默认用户 ID（可后续改为登录）
    private static final Long DEFAULT_USER_ID = 1L;

    // GET: 获取所有任务（属于默认用户）
    @GetMapping
    public ResponseEntity<List<TaskDto>> getAllTasks() {
        List<Task> tasks = taskRepository.findByUserId(DEFAULT_USER_ID);
        List<TaskDto> dtos = tasks.stream().map(this::convertToDto).collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    // GET: 获取单个任务
    @GetMapping("/{id}")
    public ResponseEntity<TaskDto> getTaskById(@PathVariable Long id) {
        return taskRepository.findByIdAndUserId(id, DEFAULT_USER_ID)
                .map(this::convertToDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST: 创建任务
    @PostMapping
    public ResponseEntity<TaskDto> createTask(@RequestBody TaskDto taskDto) {
        User user = userRepository.findById(DEFAULT_USER_ID)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Task task = new Task();
        task.setTitle(taskDto.getTitle());
        task.setDescription(taskDto.getDescription());
        task.setStatus(taskDto.getStatus() != null ? Status.valueOf(taskDto.getStatus()) : Status.PENDING);
        task.setUser(user);

        Task saved = taskRepository.save(task);
        return new ResponseEntity<>(convertToDto(saved), HttpStatus.CREATED);
    }

    // PUT: 更新任务
    @PutMapping("/{id}")
    public ResponseEntity<TaskDto> updateTask(@PathVariable Long id, @RequestBody TaskDto taskDto) {
        return taskRepository.findByIdAndUserId(id, DEFAULT_USER_ID)
                .map(task -> {
                    task.setTitle(taskDto.getTitle());
                    task.setDescription(taskDto.getDescription());
                    task.setStatus(taskDto.getStatus() != null ? Status.valueOf(taskDto.getStatus()) : task.getStatus());
                    Task updated = taskRepository.save(task);
                    return ResponseEntity.ok(convertToDto(updated));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // DELETE: 删除任务
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        if (!taskRepository.existsByIdAndUserId(id, DEFAULT_USER_ID)) {
            return ResponseEntity.notFound().build();
        }
        taskRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // 辅助方法：Entity -> DTO
    private TaskDto convertToDto(Task task) {
        TaskDto dto = new TaskDto();
        dto.setId(task.getId());
        dto.setTitle(task.getTitle());
        dto.setDescription(task.getDescription());
        dto.setStatus(task.getStatus().name());
        dto.setCreatedAt(task.getCreatedAt());
        dto.setUserId(task.getUser().getId());
        return dto;
    }
}