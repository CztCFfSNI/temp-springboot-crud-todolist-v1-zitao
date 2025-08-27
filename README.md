# 📋 TodoList REST API

![Java](https://img.shields.io/badge/Java-21-orange?logo=openjdk)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.5.5-green?logo=spring)
![MySQL](https://img.shields.io/badge/MySQL-8.0-blue?logo=mysql)

一个简单的TodoList REST API实现，提供基本的CRUD操作。

## 🚀 项目特点

- **无用户系统**：无注册、无登录、无用户权限管理
- **单任务表**：仅包含一个任务表(TodoItem)，结构简单
- **轻量级**：专注于核心功能，无额外复杂功能
- **RESTful API**：符合REST规范的标准API设计

## ⚙️ 环境要求

- **JDK 21** (必须，Spring Boot 3.x 需要 JDK 17+)
- **MySQL 8.0+** (其他版本可能需要调整连接参数)
- **Maven 3.6.3+**
- **IntelliJ IDEA** (推荐，但非必需)

## 🗄️ 数据库配置

**重要：** 项目不包含预配置的数据库连接，需要您自行配置。

1. 在 `src/main/resources/application.properties` 中添加以下配置：

```properties
# MySQL 连接配置
spring.datasource.url=jdbc:mysql://localhost:3306/tododb?useSSL=false&requireSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=xxxxxx // 如果有，改自己的密码

# JPA 配置
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
```

> **注意：**
> - `allowPublicKeyRetrieval=true` 是解决 MySQL 8.x 连接问题的关键参数
> - 如果您使用不同的数据库名，请将 `tododb` 替换为您创建的数据库名
> - 首次运行时，应用会自动创建 `todo_item` 表

## 🧪 Postman 测试指南

### 1. 创建任务 (POST)
- **URL**: `http://localhost:8080/api/todos`
- **Method**: POST
- **Headers**:
  - `Content-Type: application/json`
- **Body** (raw, JSON):
  ```json
  {
    "title": "学习Spring Boot",
    "description": "完成CRUD示例",
    "completed": false
  }
  ```
- **预期响应**: HTTP 201 Created + 创建的任务数据

### 2. 获取所有任务 (GET)
- **URL**: `http://localhost:8080/api/todos`
- **Method**: GET
- **预期响应**: HTTP 200 OK + 任务列表JSON

### 3. 更新任务 (PUT)
- **URL**: `http://localhost:8080/api/todos/{id}` (将 `{id}` 替换为任务ID)
- **Method**: PUT
- **Headers**:
  - `Content-Type: application/json`
- **Body** (raw, JSON):
  ```json
  {
    "title": "已学会Spring Boot",
    "description": "已完成CRUD示例",
    "completed": true
  }
  ```
- **预期响应**: HTTP 200 OK + 更新后的任务数据

### 4. 删除任务 (DELETE)
- **URL**: `http://localhost:8080/api/todos/{id}` (将 `{id}` 替换为任务ID)
- **Method**: DELETE
- **预期响应**: HTTP 204 No Content

## 🛠 Maven 与 JDK 配置

### 1. Maven 配置
确保您的 `pom.xml` 包含以下关键配置：

```xml
<properties>
    <java.version>21</java.version>
    <maven.compiler.source>21</maven.compiler.source>
    <maven.compiler.target>21</maven.compiler.target>
</properties>

<parent>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-parent</artifactId>
    <version>3.5.5</version> <!-- 必须使用3.x版本支持JDK 21 -->
    <relativePath/>
</parent>
```

### 2. 解决 Maven 依赖问题
如果遇到依赖下载问题（特别是国内用户）：
1. 创建或编辑 terminal输入 
```
vi ~/.m2/settings.xml
```
2. 添加阿里云镜像配置：
```xml
<mirrors>
  <mirror>
    <id>aliyunmaven</id>
    <mirrorOf>*</mirrorOf>
    <url>https://maven.aliyun.com/repository/public</url>
  </mirror>
</mirrors>
```

### 3. IntelliJ 配置要点
- **File → Project Structure → Project**:
  - Project SDK: 选择 JDK 21
  - Project language level: 21
- **File → Settings → Build, Execution, Deployment → Compiler → Java Compiler**:
  - Target bytecode version: 21
- **运行配置**:
  - 确保JRE设置为JDK 21

## 🚀 启动项目

1. 确保MySQL服务已启动
2. 在IntelliJ中运行 `TodoApiApplication.java`
3. 看到 `Tomcat started on port(s): 8080` 表示启动成功

## 🐛 常见问题

### 1. "Public Key Retrieval is not allowed" 错误
- **原因**: MySQL 8.x 连接问题
- **解决**: 确保数据库URL包含 `&allowPublicKeyRetrieval=true`

### 2. "程序包org.springframework.boot不存在" 错误
- **原因**: Maven依赖未正确下载
- **解决**:
  1. 检查Maven镜像配置
  2. 执行 Maven → Reimport
  3. File → Invalidate Caches → Invalidate and Restart

### 3. 404 Not Found 错误
- **原因**: 包结构不正确
- **解决**: 确保Controller类在主应用类的包或子包中