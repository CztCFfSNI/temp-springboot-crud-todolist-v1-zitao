package com.example.todo_api;

import com.example.todo_api.entity.User;
import com.example.todo_api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder; // ← 添加

@SpringBootApplication
public class TodoApiApplication implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder; // ← 注入

    public static void main(String[] args) {
        SpringApplication.run(TodoApiApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        if (userRepository.findByUsername("user").isEmpty()) {
            User defaultUser = User.builder()
                    .username("user")
                    .password(passwordEncoder.encode("password")) // ✅ 加密后再存！
                    .email("user@example.com")
                    .build();
            userRepository.save(defaultUser);
            System.out.println("✅ 默认用户已创建：user");
        } else {
            System.out.println("✅ 默认用户已存在");
        }
    }
}