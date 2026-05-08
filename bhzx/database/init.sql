CREATE DATABASE IF NOT EXISTS bhzx DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE bhzx;

-- ==================== 删除现有表 ====================
DROP TABLE IF EXISTS score;
DROP TABLE IF EXISTS student_course;
DROP TABLE IF EXISTS homework_submission;
DROP TABLE IF EXISTS homework;
DROP TABLE IF EXISTS knowledge_point;
DROP TABLE IF EXISTS course;
DROP TABLE IF EXISTS user;

-- ==================== 建表语句 ====================

-- 1. 用户表（统一管理学生、教师、管理员）
CREATE TABLE user (
                      id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '用户ID',
                      username VARCHAR(50) NOT NULL UNIQUE COMMENT '用户名',
                      password VARCHAR(100) NOT NULL COMMENT '密码（明文，生产环境应加密）',
                      name VARCHAR(50) NOT NULL COMMENT '真实姓名',
                      role VARCHAR(20) NOT NULL COMMENT '角色：ADMIN/TEACHER/STUDENT',
                      gender VARCHAR(10) COMMENT '性别',

                      create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                      update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',

                      INDEX idx_username (username),
                      INDEX idx_role (role)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

-- 2. 课程表
CREATE TABLE course (
                        id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '课程ID',
                        course_no VARCHAR(30) NOT NULL UNIQUE COMMENT '课程编号',
                        course_name VARCHAR(100) NOT NULL COMMENT '课程名称',
                        credit INT NOT NULL COMMENT '学分',
                        hours INT COMMENT '学时',
                        description TEXT COMMENT '课程描述',
                        teacher_id BIGINT COMMENT '授课教师ID',
                        max_student INT DEFAULT 50 COMMENT '最大选课人数',
                        status VARCHAR(20) DEFAULT 'ACTIVE' COMMENT '状态：ACTIVE/INACTIVE',
                        create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',

                        FOREIGN KEY (teacher_id) REFERENCES user(id) ON DELETE SET NULL,
                        INDEX idx_course_no (course_no),
                        INDEX idx_teacher_id (teacher_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='课程表';

-- 3. 学生选课表
CREATE TABLE student_course (
                                id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '选课ID',
                                student_id BIGINT NOT NULL COMMENT '学生ID',
                                course_id BIGINT NOT NULL COMMENT '课程ID',
                                score DECIMAL(5,2) COMMENT '成绩',
                                select_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '选课时间',
                                status VARCHAR(20) DEFAULT 'SELECTED' COMMENT '状态：SELECTED/DROPPED/COMPLETED',

                                UNIQUE KEY uk_student_course (student_id, course_id),
                                FOREIGN KEY (student_id) REFERENCES user(id) ON DELETE CASCADE,
                                FOREIGN KEY (course_id) REFERENCES course(id) ON DELETE CASCADE,
                                INDEX idx_student_id (student_id),
                                INDEX idx_course_id (course_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='选课表';

-- 4. 作业表
CREATE TABLE homework (
                          id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '作业ID',
                          course_id BIGINT NOT NULL COMMENT '课程ID',
                          title VARCHAR(200) NOT NULL COMMENT '作业标题',
                          content TEXT COMMENT '作业内容',
                          deadline DATETIME COMMENT '截止时间',
                          create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',

                          FOREIGN KEY (course_id) REFERENCES course(id) ON DELETE CASCADE,
                          INDEX idx_course_id (course_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='作业表';

-- 5. 作业提交表
CREATE TABLE homework_submission (
                                     id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '提交ID',
                                     homework_id BIGINT NOT NULL COMMENT '作业ID',
                                     student_id BIGINT NOT NULL COMMENT '学生ID',
                                     content TEXT COMMENT '提交内容',
                                     score DECIMAL(5,2) COMMENT '得分',
                                     comment TEXT COMMENT '教师评语',
                                     submit_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '提交时间',

                                     UNIQUE KEY uk_homework_student (homework_id, student_id),
                                     FOREIGN KEY (homework_id) REFERENCES homework(id) ON DELETE CASCADE,
                                     FOREIGN KEY (student_id) REFERENCES user(id) ON DELETE CASCADE,
                                     INDEX idx_homework_id (homework_id),
                                     INDEX idx_student_id (student_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='作业提交表';

-- 6. 知识点表（树状结构）
CREATE TABLE knowledge_point (
                                 id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '知识点ID',
                                 course_id BIGINT NOT NULL COMMENT '课程ID',
                                 parent_id BIGINT DEFAULT 0 COMMENT '父知识点ID，0表示根节点',
                                 name VARCHAR(100) NOT NULL COMMENT '知识点名称',
                                 description TEXT COMMENT '知识点描述',
                                 sort_order INT DEFAULT 0 COMMENT '排序',
                                 create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',

                                 FOREIGN KEY (course_id) REFERENCES course(id) ON DELETE CASCADE,
                                 INDEX idx_course_id (course_id),
                                 INDEX idx_parent_id (parent_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='知识点表';

-- ==================== 初始化测试数据 ====================

-- 插入用户数据（密码都是：123456）
INSERT INTO user (username, password, name, role, gender) VALUES
                                                              -- 管理员
                                                              ('admin', '123456', '系统管理员', 'ADMIN', NULL),

                                                              -- 教师（3个）
                                                              ('teacher1', '123456', '张老师', 'TEACHER', '男'),
                                                              ('teacher2', '123456', '李老师', 'TEACHER', '女'),
                                                              ('teacher3', '123456', '王老师', 'TEACHER', '男'),

                                                              -- 学生（3个）
                                                              ('student1', '123456', '小明', 'STUDENT', '男'),
                                                              ('student2', '123456', '小红', 'STUDENT', '女'),
                                                              ('student3', '123456', '小刚', 'STUDENT', '男');

-- 插入课程数据
INSERT INTO course (course_no, course_name, credit, hours, description, teacher_id, max_student) VALUES
                                                                                                     ('C001', '高等数学', 4, 64, '大学数学基础课程', 4, 60),
                                                                                                     ('C002', '数据结构', 3, 48, '计算机核心课程', 2, 50),
                                                                                                     ('C003', '软件工程', 3, 48, '软件开发方法论', 3, 50),
                                                                                                     ('C004', '数据库原理', 3, 48, '数据库理论与应用', 2, 50);

-- 插入选课数据
INSERT INTO student_course (student_id, course_id, status) VALUES
                                                               (5, 1, 'SELECTED'),  -- 小明选了高等数学
                                                               (5, 2, 'SELECTED'),  -- 小明选了数据结构
                                                               (6, 1, 'SELECTED'),  -- 小红选了高等数学
                                                               (6, 3, 'SELECTED'),  -- 小红选了软件工程
                                                               (7, 2, 'SELECTED'),  -- 小刚选了数据结构
                                                               (7, 4, 'SELECTED');  -- 小刚选了数据库原理

-- 插入作业数据
INSERT INTO homework (course_id, title, content, deadline) VALUES
                                                               (2, '第一次作业：线性表', '完成线性表的实现和测试', '2025-12-31 23:59:59'),
                                                               (2, '第二次作业：树和图', '实现二叉树遍历算法', '2026-01-15 23:59:59'),
                                                               (3, '第一次作业：需求分析', '编写软件需求规格说明书', '2026-01-10 23:59:59');

-- 插入知识点数据（以数据结构课程为例）
INSERT INTO knowledge_point (course_id, parent_id, name, description, sort_order) VALUES
                                                                                      -- 根节点
                                                                                      (2, 0, '数据结构', '计算机科学基础课程', 1),

                                                                                      -- 第一层子节点
                                                                                      (2, 1, '线性表', '线性结构的基础知识', 1),
                                                                                      (2, 1, '树和二叉树', '树形结构', 2),
                                                                                      (2, 1, '图论', '图的基本概念和算法', 3),
                                                                                      (2, 1, '排序算法', '各种排序方法', 4),

                                                                                      -- 第二层子节点（线性表的子节点）
                                                                                      (2, 2, '顺序表', '数组实现的线性表', 1),
                                                                                      (2, 2, '链表', '链式存储的线性表', 2),
                                                                                      (2, 2, '栈和队列', '特殊的线性表', 3);
