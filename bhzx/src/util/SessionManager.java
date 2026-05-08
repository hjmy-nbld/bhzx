package util;

import entity.User;

public class SessionManager {
    private static User currentUser = null;
    
    /**
     * 登录
     */
    public static void login(User user) {
        currentUser = user;
    }
    
    /**
     * 登出
     */
    public static void logout() {
        currentUser = null;
    }
    
    /**
     * 获取当前用户
     */
    public static User getCurrentUser() {
        return currentUser;
    }
    
    /**
     * 检查是否已登录
     */
    public static boolean isLoggedIn() {
        return currentUser != null;
    }
    
    /**
     * 检查是否为管理员
     */
    public static boolean isAdmin() {
        return currentUser != null && "ADMIN".equals(currentUser.getRole());
    }
    
    /**
     * 检查是否为教师
     */
    public static boolean isTeacher() {
        return currentUser != null && "TEACHER".equals(currentUser.getRole());
    }
    
    /**
     * 检查是否为学生
     */
    public static boolean isStudent() {
        return currentUser != null && "STUDENT".equals(currentUser.getRole());
    }
}
