// src/main/java/com/example/todo_api/config/DataInitializer.java
package com.example.todo_api.config;

import com.example.todo_api.entity.User;
import com.example.todo_api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void run(String... args) throws Exception {
        if (userRepository.findByUsername("default_user").isEmpty()) {
            User user = new User();
            user.setUsername("default_user");
            user.setEmail("user@todo.com");
            userRepository.save(user);
            System.out.println("✅ 默认用户已创建: default_user");
        }
    }
}