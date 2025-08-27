package com.example.todo_api.controller;

import com.example.todo_api.entity.TodoItem;
import com.example.todo_api.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/todos")
public class TodoController {

    @Autowired
    private TodoRepository todoRepository;

    // GET 所有任务
    @GetMapping
    public List<TodoItem> getAllTodos() {
        return todoRepository.findAll();
    }

    // POST 创建任务
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TodoItem createTodo(@RequestBody TodoItem todoItem) {
        return todoRepository.save(todoItem);
    }

    // PUT 更新任务
    @PutMapping("/{id}")
    public TodoItem updateTodo(@PathVariable Long id, @RequestBody TodoItem todoItem) {
        todoItem.setId(id);
        return todoRepository.save(todoItem);
    }

    // DELETE 删除任务
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTodo(@PathVariable Long id) {
        todoRepository.deleteById(id);
    }
}