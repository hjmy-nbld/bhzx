package controller;

import entity.User;
import service.UserService;
import util.MenuUtil;
import util.SessionManager;

import java.util.List;

public class AdminController {
    private final UserService userService = new UserService();
    
    public void manage() {
        while (true) {
            MenuUtil.printAdminMenu();
            int choice = MenuUtil.getIntInput("");
            
            switch (choice) {
                case 1:
                    manageUsers();
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
     * 用户管理
     */
    private void manageUsers() {
        System.out.println("\n===== 用户列表 =====");
        List<User> users = userService.getAllUsers();
        
        if (users.isEmpty()) {
            System.out.println("暂无用户");
            return;
        }
        
        System.out.printf("| %-3s | %-15s | %-10s | %-10s |%n",
                "ID", "用户名", "姓名", "角色");
        System.out.println("--------------------------------------------------------");
        for (User user : users) {
            System.out.printf("| %-3d | %-15s | %-10s | %-10s |%n",
                    user.getId(), user.getUsername(), user.getName(), user.getRole());
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
