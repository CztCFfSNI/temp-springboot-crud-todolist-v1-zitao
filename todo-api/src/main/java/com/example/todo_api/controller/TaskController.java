package com.example.todo_api.controller;

import com.example.todo_api.dto.TaskDto;
import com.example.todo_api.entity.Task;
import com.example.todo_api.entity.User;
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
@CrossOrigin // 可选，为未来前端准备
public class TaskController {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    // 获取所有任务（默认用户）
    @GetMapping
    public ResponseEntity<List<TaskDto>> getAllTasks() {
        User user = userRepository.findByUsername("user")
                .orElseThrow(() -> new RuntimeException("默认用户不存在"));

        List<TaskDto> tasks = taskRepository.findByUserId(user.getId()).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok(tasks);
    }

    // 获取单个任务
    @GetMapping("/{id}")
    public ResponseEntity<TaskDto> getTaskById(@PathVariable Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("任务不存在"));

        return ResponseEntity.ok(convertToDto(task));
    }

    // 创建任务
    @PostMapping
    public ResponseEntity<TaskDto> createTask(@RequestBody TaskDto taskDto) {
        User user = userRepository.findByUsername("user")
                .orElseThrow(() -> new RuntimeException("默认用户不存在"));

        Task task = convertToEntity(taskDto);
        task.setUser(user);

        Task saved = taskRepository.save(task);
        return new ResponseEntity<>(convertToDto(saved), HttpStatus.CREATED);
    }

    // 更新任务
    @PutMapping("/{id}")
    public ResponseEntity<TaskDto> updateTask(@PathVariable Long id, @RequestBody TaskDto taskDto) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("任务不存在"));

        task.setTitle(taskDto.getTitle());
        task.setDescription(taskDto.getDescription());
        task.setCompleted(taskDto.getCompleted());

        Task updated = taskRepository.save(task);
        return ResponseEntity.ok(convertToDto(updated));
    }

    // 删除任务
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        if (!taskRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        taskRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // 转换 Entity <-> DTO
    private TaskDto convertToDto(Task task) {
        TaskDto dto = new TaskDto();
        dto.setId(task.getId());
        dto.setTitle(task.getTitle());
        dto.setDescription(task.getDescription());
        dto.setCompleted(task.getCompleted());
        dto.setCreatedAt(task.getCreatedAt());
        dto.setUpdatedAt(task.getUpdatedAt());
        dto.setUserId(task.getUser().getId());
        return dto;
    }

    private Task convertToEntity(TaskDto dto) {
        Task task = new Task();
        task.setId(dto.getId());
        task.setTitle(dto.getTitle());
        task.setDescription(dto.getDescription());
        task.setCompleted(dto.getCompleted());
        return task;
    }
}