# 📋 TodoList REST API

![Java](https://img.shields.io/badge/Java-21-orange?logo=openjdk)  
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.5.5-green?logo=spring)  
![MySQL](https://img.shields.io/badge/MySQL-8.0-blue?logo=mysql)  
![Docker](https://img.shields.io/badge/Docker-✔-blue?logo=docker)  
![iOS](https://img.shields.io/badge/iOS-Swift-blue?logo=swift)

一个基于 **Spring Boot 3 + JDK 21 + MySQL 8 + Docker + Swift iOS** 的完整 TodoList 应用系统，支持后端 REST API 与跨平台前端（Web + iOS）的 CRUD 操作。

> 🔐 当前版本使用预设默认用户，所有任务操作自动绑定该用户。适合快速开发与演示。

---

## 🚀 项目特点

- ✅ **全栈一体化**：Spring Boot 后端 + HTML 前端 + SwiftUI iOS 客户端
- ✅ **默认用户绑定**：系统初始化自动创建用户 `user`，所有任务归属该用户
- ✅ **Docker 化数据库**：使用 MySQL 8 容器部署，无需本地安装
- ✅ **RESTful API**：标准化接口，支持增删改查（CRUD）
- ✅ **跨平台访问**：支持浏览器、iOS 模拟器、真机访问
- ✅ **前端集成**：内置静态 HTML 页面，开箱即用
- ✅ **iOS 客户端**：Swift + SwiftUI 实现，支持网络请求与刷新

---

## 🧩 技术栈

| 层级       | 技术 |
|------------|------|
| 后端框架   | Spring Boot 3.5.5 |
| Java 版本  | JDK 21 |
| 数据库     | MySQL 8 (Docker) |
| ORM        | Spring Data JPA |
| 构建工具   | Maven |
| 前端       | HTML/CSS/JS (静态资源) |
| 移动端     | SwiftUI (Swift) |
| 容器化     | Docker |
| IDE        | IntelliJ IDEA + Xcode |

---

## ⚙️ 环境要求

- **JDK 21**
- **Maven 3.6.3+**
- **Docker**（用于运行 MySQL）
- **IntelliJ IDEA**（推荐）
- **Xcode**（iOS 开发）
- **局域网环境**（用于 iOS 访问后端）

---

## 🗄️ 数据库配置（Docker）

### 1. 启动 MySQL 容器

```bash
docker run -d \
  --name todolist_v2 \
  -p 3307:3306 \
  -e MYSQL_ROOT_PASSWORD=xxxxx \
  -e MYSQL_DATABASE=tododb \
  mysql:8
```

> ✅ 容器启动后，数据库 `tododb` 将自动创建。

### 2. 验证容器运行

```bash
docker ps
```

应看到：

```
CONTAINER ID   IMAGE     PORTS                    NAMES
bc7c1a67da31   mysql:8   0.0.0.0:3307->3306/tcp   todolist_v2
```

---

## 🛠 应用配置

### 1. 配置 `src/main/resources/application.yml`

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3307/tododb?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
    username: root
    password: xxxxx # 自己的密码
    driver-class-name: com.mysql.cj.jdbc.Driver

  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true
    open-in-view: false
```

> 🔁 注意：
> - `allowPublicKeyRetrieval=true` 是连接 MySQL 8 的关键
> - 确保 `tododb` 数据库已存在
> - 端口 `3307` 映射到容器 `3306`

---

## 🚀 启动后端服务

### 1. 编译并运行

```bash
./mvnw spring-boot:run
```

### 2. 验证启动成功

看到日志：

```
Tomcat started on port 8080
Started TodoApiApplication in X seconds
✅ 默认用户已创建：user
```

### 3. 测试 API

- 查看任务列表：`http://localhost:8080/api/tasks` → 返回 `[]`
- 查看首页：`http://localhost:8080/` → 显示 HTML 页面

---

## 🖥️ 前端页面（HTML）

### 1. 文件位置

```
src/main/resources/static/index.html
```

### 2. 功能

- 显示任务列表
- 支持添加、删除、标记完成
- 自动刷新

### 3. 访问地址

👉 `http://localhost:8080/`

---

## 📱 iOS 客户端（Swift + SwiftUI）

### 1. 项目名称

`TodoListApp`（Xcode 项目）

### 2. 运行步骤

1. 打开 Xcode → 打开项目 `TodoListApp.xcodeproj`
2. 在 `ContentView.swift` 中修改 IP 地址：

```swift
private let baseURL = "http://192.168.0.100:8080/api/tasks"
```

> 🔁 将 `192.168.0.100` 替换为你电脑的局域网 IP（通过 `ifconfig` 查看）

3. 在顶部选择模拟器（如 iPhone 15）
4. 点击 ▶️ 运行

### 3. 配置 `Info.plist`（允许 HTTP）

```xml
<key>NSAppTransportSecurity</key>
<dict>
    <key>NSAllowsArbitraryLoads</key>
    <true/>
</dict>
```

### 4. 功能

- 下拉刷新获取任务
- 显示标题、描述、状态
- 支持跨设备访问（需同一 Wi-Fi）

---

## 🧪 Postman 测试指南

| 方法 | 路径 | 描述 |
|------|------|------|
| `GET` | `http://localhost:8080/api/tasks` | 获取所有任务 |
| `POST` | `http://localhost:8080/api/tasks` | 创建任务 |
| `GET` | `http://localhost:8080/api/tasks/{id}` | 获取单个任务 |
| `PUT` | `http://localhost:8080/api/tasks/{id}` | 更新任务 |
| `DELETE` | `http://localhost:8080/api/tasks/{id}` | 删除任务 |

### 示例：创建任务（POST）

```json
{
  "title": "学习 Spring Boot",
  "description": "完成 CRUD 示例",
  "status": "PENDING"
}
```

> ✅ 响应状态：`201 Created`

---

## 📂 项目结构

```
src/main/java/com/example/todo_api/
├── TodoApiApplication.java         # 主启动类
├── controller/
│   └── TaskController.java         # REST API 控制器
├── entity/
│   ├── User.java                   # 用户实体
│   ├── Task.java                   # 任务实体
│   └── Status.java                 # 任务状态枚举
├── repository/
│   ├── UserRepository.java         # 用户数据访问
│   └── TaskRepository.java         # 任务数据访问
├── dto/
│   └── TaskDto.java                # 数据传输对象
└── config/
    ├── DataInitializer.java        # 初始化默认用户
    └── WebConfig.java              # 静态资源路由
```

---

## 🐛 常见问题与解决

### 1. `allowPublicKeyRetrieval` 错误

- **现象**：`Public Key Retrieval is not allowed`
- **解决**：确保 URL 包含 `&allowPublicKeyRetrieval=true`

### 2. `index.html` 404

- **原因**：文件未放在 `src/main/resources/static/`
- **解决**：
  - 确认路径正确
  - 执行 `mvn clean compile`
  - 添加 `HomeController` 显式映射 `/`

### 3. Lombok 注解标红

- **解决**：
  1. 安装 Lombok 插件（IntelliJ → Plugins）
  2. 启用 Annotation Processing
  3. 重启 IDE

### 4. iOS 无法连接后端

- **原因**：IP 错误或网络不通
- **解决**：
  - 使用 `ifconfig` 查看电脑 IP
  - 确保手机/模拟器与电脑在同一 Wi-Fi
  - 测试 `http://你的IP:8080/api/tasks` 是否可访问

---

## 🚀 下一步计划

| 功能 | 状态 |
|------|------|
| ✅ 后端 API + 数据库 | ✔️ |
| ✅ 前端 HTML 页面 | ✔️ |
| ✅ iOS Swift 客户端 | ✔️ |
| 🔜 JWT 用户认证 | ❌ |
| 🔜 用户注册/登录 | ❌ |
| 🔜 密码加密（BCrypt） | ❌ |
| 🔜 全局异常处理 | ❌ |
| 🔜 Swagger API 文档 | ❌ |
| 🔜 部署到云服务器 | ❌ |
