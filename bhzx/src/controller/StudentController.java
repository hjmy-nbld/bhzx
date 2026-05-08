package controller;

import entity.Course;
import service.CourseService;
import util.MenuUtil;
import util.SessionManager;

import java.util.List;

public class StudentController {
    private final CourseService courseService = new CourseService();
    
    public void manage() {
        while (true) {
            MenuUtil.printStudentMenu();
            int choice = MenuUtil.getIntInput("");
            
            switch (choice) {
                case 1:
                    viewCourses();
                    break;
                case 2:
                    System.out.println("\n待实现");
                    break;
                case 3:
                    updateProfile();
                    break;
                case 4:
                    SessionManager.logout();
                    System.out.println("已退出登录");
                    return;
                default:
                    System.out.println("无效选择，请重新输入");
            }
        }
    }
    
    /**
     * 查看所有课程信息
     */
    private void viewCourses() {
        System.out.println("\n===== 课程列表 =====");
        List<Course> courses = courseService.getAllCourses();
        
        if (courses.isEmpty()) {
            System.out.println("暂无课程");
            return;
        }
        
        System.out.printf("| %-2s | %-10s | %-20s | %-4s | %-4s | %-15s | %-30s |%n",
                "ID", "课程编号", "课程名称", "学分", "学时", "授课教师", "状态");
        System.out.println("------------------------------------------------------------------------------------------------------------");
        for (Course course : courses) {
            System.out.println(course);
        }
    }
    
    /**
     * 修改个人资料
     */
    private void updateProfile() {
        System.out.println("\n===== 修改个人资料 =====");
        System.out.println("当前用户: " + SessionManager.getCurrentUser());
        System.out.println("待实现");
    }
}
