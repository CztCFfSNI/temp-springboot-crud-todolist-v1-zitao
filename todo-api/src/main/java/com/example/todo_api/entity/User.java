// src/main/java/com/example/todo_api/entity/User.java
package com.example.todo_api.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "user")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder // ✅ 添加这一行
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    private String password; // 注意：明文密码仅用于测试！

    private String email;
}