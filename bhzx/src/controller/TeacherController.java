package controller;

import entity.Course;
import service.CourseService;
import util.MenuUtil;
import util.SessionManager;

import java.util.List;

public class TeacherController {
    private final CourseService courseService = new CourseService();
    
    public void manage() {
        while (true) {
            MenuUtil.printTeacherMenu();
            int choice = MenuUtil.getIntInput("");
            
            switch (choice) {
                case 1:
                    manageCourses();
                    break;
                case 2:
                    System.out.println("\n待实现");
                    break;
                case 3:
                    System.out.println("\n待实现");
                    break;
                case 4:
                    System.out.println("\n待实现");
                    break;
                case 5:
                    updateProfile();
                    break;
                case 6:
                    SessionManager.logout();
                    System.out.println("已退出登录");
                    return;
                default:
                    System.out.println("无效选择，请重新输入");
            }
        }
    }
    
    /**
     * 管理课程
     */
    private void manageCourses() {
        System.out.println("\n===== 我的课程 =====");
        Long teacherId = SessionManager.getCurrentUser().getId();
        List<Course> courses = courseService.getCoursesByTeacherId(teacherId);
        
        if (courses.isEmpty()) {
            System.out.println("暂无授课课程");
            return;
        }
        
        System.out.printf("| %-2s | %-10s | %-20s | %-4s | %-4s | %-30s |%n",
                "ID", "课程编号", "课程名称", "学分", "学时", "状态");
        System.out.println("------------------------------------------------------------------------------------");
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
