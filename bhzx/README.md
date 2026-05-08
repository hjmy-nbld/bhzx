# 教务管理系统 - 使用说明

## 📋 系统概述

这是一个基于角色的教务管理系统，支持三种用户角色：管理员、教师、学生。

---

## 🚀 快速开始

### 1. 初始化数据库

在 Windows cmd 中执行：

```cmd
mysql -u root -p < "E:\class tools\eclipse\workspace\bhzx\database\init.sql"
```

### 2. 编译并运行项目

使用 Eclipse 或 IntelliJ IDEA 打开项目，运行 `Main.java`

### 3. 登录系统

主菜单选择 `1. 登录`，然后输入用户名和密码。

---

## 👥 测试账号

| 角色 | 用户名 | 密码 | 姓名 | 功能说明 |
|------|--------|------|------|----------|
| 管理员 | admin | 123456 | 系统管理员 | 用户管理、课程管理等 |
| 教师 | teacher1 | 123456 | 张老师 | 查看授课课程 |
| 教师 | teacher2 | 123456 | 李老师 | 查看授课课程 |
| 教师 | teacher3 | 123456 | 王老师 | 查看授课课程 |
| 学生 | student1 | 123456 | 小明 | 查看所有课程 |
| 学生 | student2 | 123456 | 小红 | 查看所有课程 |
| 学生 | student3 | 123456 | 小刚 | 查看所有课程 |

---

## 🎯 已实现功能

### ✅ 第一阶段：用户认证（已完成）

- [x] 用户登录/登出
- [x] 基于角色的菜单
- [x] 会话管理

### ✅ 第二阶段：基础功能（部分完成）

#### 学生功能
- [x] 查看所有课程信息（含教师、学分、学时等）
- [ ] 完成作业（待第三阶段）
- [x] 修改个人资料（框架）

#### 教师功能
- [x] 查看授课课程列表
- [ ] 管理选课学生（待第三阶段）
- [ ] 管理作业（待第三阶段）
- [ ] 管理知识点（待第三阶段）
- [x] 修改个人资料（框架）

#### 管理员功能
- [x] 查看所有用户列表
- [ ] 课程管理（待扩展）
- [ ] 任课教师管理（待扩展）
- [ ] 选课学生管理（待扩展）
- [x] 修改个人资料（框架）

---

## 📁 项目结构

```
bhzx/
├── database/
│   └── init.sql                    # 数据库初始化脚本
├── src/
│   ├── controller/                 # 控制器层
│   │   ├── LoginController.java    # 登录控制器
│   │   ├── AdminController.java    # 管理员控制器
│   │   ├── TeacherController.java  # 教师控制器
│   │   └── StudentController.java  # 学生控制器
│   ├── service/                    # 服务层
│   │   ├── UserService.java        # 用户服务
│   │   └── CourseService.java      # 课程服务
│   ├── dao/                        # 数据访问层
│   │   ├── UserDAO.java            # 用户DAO
│   │   └── CourseDAO.java          # 课程DAO
│   ├── entity/                     # 实体类
│   │   ├── User.java               # 用户实体
│   │   └── Course.java             # 课程实体
│   ├── util/                       # 工具类
│   │   ├── MenuUtil.java           # 菜单工具
│   │   ├── SessionManager.java     # 会话管理
│   │   └── DBUtil.java             # 数据库工具
│   └── Main.java                   # 主程序入口
└── README.md                       # 本文件
```

---

## 🔧 技术栈

- **后端**: Java 8
- **数据库**: MySQL 8.0
- **连接方式**: JDBC
- **架构模式**: MVC分层架构

---

## 📝 数据库表结构

### 核心表

1. **user** - 用户表（统一管理学生、教师、管理员）
   - 字段：id, username, password, name, role, student_id, teacher_id, gender, age, class_name, department, title

2. **course** - 课程表
   - 字段：id, course_no, course_name, credit, hours, description, teacher_id, max_student, status

3. **student_course** - 选课表
4. **homework** - 作业表
5. **homework_submission** - 作业提交表
6. **knowledge_point** - 知识点表

---

## 🔄 下一步开发计划

### 第三阶段：高级功能

1. **学生模块**
   - 选课/退课
   - 查看已选课程
   - 查看成绩
   - 提交作业

2. **教师模块**
   - 添加/编辑课程
   - 查看选课学生
   - 发布作业
   - 批改作业
   - 管理知识点树

3. **管理员模块**
   - 添加/编辑/删除用户
   - 分配教师到课程
   - 管理选课
   - 统计数据

4. **通用功能**
   - 修改密码
   - 编辑个人资料
   - 数据导出

---

## 💡 扩展建议

### 如何添加新功能？

1. **创建实体类** - 在 `entity` 包中定义数据模型
2. **创建 DAO** - 在 `dao` 包中实现数据库操作
3. **创建 Service** - 在 `service` 包中实现业务逻辑
4. **创建 Controller** - 在 `controller` 包中实现用户交互
5. **更新菜单** - 在 `MenuUtil` 中添加菜单项

### 代码示例

参考现有的 `Course` 相关类的实现模式。

---

## ⚠️ 注意事项

1. **密码安全**: 当前使用明文存储，生产环境应使用 BCrypt 加密
2. **异常处理**: 建议添加更完善的异常处理机制
3. **输入验证**: 关键输入需要加强验证
4. **事务管理**: 涉及多表操作时应使用事务

---

## 📞 技术支持

如有问题，请检查：
1. 数据库是否正确初始化
2. MySQL 服务是否启动
3. 数据库连接配置（DBUtil.java）
4. 控制台错误信息

---

**版本**: v1.0  
**更新日期**: 2026-05-06
