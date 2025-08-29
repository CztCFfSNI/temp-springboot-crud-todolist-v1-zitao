# 📋 TodoList REST API

![Java](https://img.shields.io/badge/Java-21-orange?logo=openjdk)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.5.5-green?logo=spring)
![MySQL](https://img.shields.io/badge/MySQL-8.0-blue?logo=mysql)

一个简单的 TodoList REST API 实现，基于 Spring Boot 3 + JDK 21 + MySQL 8，支持用户绑定任务的 CRUD 操作。

> 🔐 当前版本暂未实现注册/登录功能，所有操作默认绑定到一个预设的默认用户。

---

## 🚀 项目特点

- **默认用户绑定**：系统初始化时创建默认用户 `user`，所有任务操作均绑定该用户
- **双表结构**：包含 `user` 和 `task` 两张表，支持未来扩展用户系统
- **JWT 预留**：架构设计为未来集成 JWT 认证做准备
- **Docker 化数据库**：使用 Docker 部署 MySQL，避免本地环境依赖
- **RESTful API**：符合 REST 规范的标准化接口设计

---

## ⚙️ 环境要求

- **JDK 21**（Spring Boot 3.x 必需）
- **MySQL 8.0+**（推荐使用 Docker 部署）
- **Maven 3.6.3+**
- **Docker**（用于数据库容器化）
- **IntelliJ IDEA**（推荐）

---

## 🗄️ 应用配置

### 1. 配置 `src/main/resources/application.yml`

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3307/tododb?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true&allowPublicKeyRetrieval=true # 端口别占用，这个很重要，查看通过：lsof -i :3307
    username: root
    password: xxxxx # 换自己密码
    driver-class-name: com.mysql.cj.jdbc.Driver

  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true
    open-in-view: false
```

> 🔁 注意：
>
> - 使用 `application.yml`（不是 `.properties`）
> - `allowPublicKeyRetrieval=true` 是连接 MySQL 8 的关键
> - 推荐使用 `appuser` 而非 `root` 连接

---

## 🧪 Postman 测试指南

### 1. 获取所有任务 (GET)

- **URL**: `http://localhost:8080/api/tasks`
- **Method**: GET
- **预期响应**: 返回默认用户的所有任务列表

### 2. 创建任务 (POST)

- **URL**: `http://localhost:8080/api/tasks`
- **Method**: POST
- **Headers**:
  - `Content-Type: application/json`
- **Body** (JSON):
  ```json
  {
    "title": "学习 Spring Boot",
    "description": "完成 CRUD 示例",
    "completed": false
  }
  ```
- **预期响应**: `201 Created` + 新建任务数据

### 3. 获取单个任务 (GET)

- **URL**: `http://localhost:8080/api/tasks/{id}`
- **Method**: GET
- **预期响应**: `200 OK` + 任务详情

### 4. 更新任务 (PUT)

- **URL**: `http://localhost:8080/api/tasks/{id}`
- **Method**: PUT
- **Headers**:
  - `Content-Type: application/json`
- **Body**:
  ```json
  {
    "title": "已学会 Spring Boot",
    "completed": true
  }
  ```
- **预期响应**: `200 OK` + 更新后数据

### 5. 删除任务 (DELETE)

- **URL**: `http://localhost:8080/api/tasks/{id}`
- **Method**: DELETE
- **预期响应**: `204 No Content`

---

## 🛠 Maven 与 JDK 配置

### 1. `pom.xml` 关键配置

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>3.5.5</version>
        <relativePath/> <!-- lookup parent from repository -->
    </parent>

    <groupId>com.example</groupId>
    <artifactId>todo-api</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <name>todo-api</name>
    <description>Todo List Plain Version</description>

    <!-- 可选：设置项目 URL -->
    <url>http://localhost:8080</url>

    <!-- 修复：添加实际的 license -->
    <licenses>
        <license>
            <name>Apache License, Version 2.0</name>
            <url>https://www.apache.org/licenses/LICENSE-2.0</url>
            <distribution>repo</distribution>
        </license>
    </licenses>

    <!-- 可选：添加开发者信息 -->
    <developers>
        <developer>
            <name>Your Name</name>
            <email>you@example.com</email>
            <organization>Personal</organization>
            <organizationUrl>http://localhost</organizationUrl>
        </developer>
    </developers>

    <!-- 可选：SCM 信息（Git） -->
    <scm>
        <connection>scm:git:https://github.com/yourname/todo-api.git</connection>
        <developerConnection>scm:git:https://github.com/yourname/todo-api.git</developerConnection>
        <url>https://github.com/yourname/todo-api</url>
        <tag>HEAD</tag>
    </scm>

    <properties>
        <java.version>21</java.version>
        <!-- 显式声明插件版本（可选，Parent 已包含，但显式更清晰） -->
        <maven.compiler.source>21</maven.compiler.source>
        <maven.compiler.target>21</maven.compiler.target>
    </properties>

    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-jpa</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>

        <!-- MySQL Driver -->
        <dependency>
            <groupId>com.mysql</groupId>
            <artifactId>mysql-connector-j</artifactId>
            <scope>runtime</scope>
        </dependency>

        <!-- Lombok: 添加这一项，解决 @Getter/@Setter 标红 -->
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <optional>true</optional>
        </dependency>

        <!-- Test -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
                <configuration>
                    <excludes>
                        <exclude>
                            <groupId>org.projectlombok</groupId>
                            <artifactId>lombok</artifactId>
                        </exclude>
                    </excludes>
                </configuration>
            </plugin>
        </plugins>
    </build>

</project>
```

---

## 🚀 启动项目

1. 启动数据库：`docker-compose up -d`
2. 在 IntelliJ 中运行 `TodoApiApplication.java`
3. 看到日志：
   ```
   Tomcat started on port 8080
   Started TodoApiApplication in X seconds
   ✅ 默认用户已创建：user
   ```
4. 使用 Postman 测试 API

---

## 🐛 常见问题

### 1. `Public Key Retrieval is not allowed`

- **原因**：MySQL 8 认证插件问题
- **解决**：确保连接 URL 包含 `&allowPublicKeyRetrieval=true`

### 2. `SHOW TABLES` 看不到表

- **可能原因**：
  - 未启动 `docker-compose`
  - 未正确设置 `MYSQL_DATABASE=tododb`
  - 应用未启动（表由 Hibernate 自动创建）
- **解决**：确认容器运行，重启 Spring Boot 应用

### 3. Lombok 注解标红

- **解决**：
  1. 安装 Lombok 插件（IntelliJ → Plugins）
  2. 启用 Annotation Processing
  3. 重启 IDE

### 4. 404 Not Found

- **原因**：Controller 路径错误
- **解决**：确认 `TaskController` 的 `@RequestMapping("/api/tasks")` 正确

---

## 📂 项目结构

```
src/main/java/com/example/todo_api/
├── TodoApiApplication.java
├── controller/TaskController.java
├── entity/User.java
├── entity/Task.java
├── repository/UserRepository.java
├── repository/TaskRepository.java
└── dto/TaskDto.java
```

---

## 🚀 下一步计划

- ✅ 实现 JWT 用户认证
- ✅ 添加注册/登录接口
- ✅ 密码加密（BCrypt）
- ✅ 全局异常处理
- ✅ Swagger API 文档

```
