package controller;

import entity.User;
import service.UserService;
import util.MenuUtil;
import util.SessionManager;

public class LoginController {
    private UserService userService = new UserService();
    
    public void login() {
        System.out.println("\n===== 用户登录 =====");
        Long userId = null;

        // 1. 输入用户 ID
        while (true) {
            String idStr = MenuUtil.getStringInput("请输入用户 ID: ");
            try {
                userId = Long.parseLong(idStr.trim());
                break;
            } catch (NumberFormatException e) {
                System.out.println("请输入有效的ID！");
            }
        }

        // 2. 检查账号是否存在
        if (!userService.isUserExists(userId)) {
            System.out.println("账号不存在！");
            return;
        }

        // 3. 输入密码
        String password = MenuUtil.getStringInput("请输入密码: ");

        // 4. 验证密码并登录
        User loginUser = userService.login(userId, password);
        if (loginUser == null) {
            System.out.println("密码错误！");
            return;
        }

        // 5. 登录成功，记录会话
        SessionManager.login(loginUser);
        System.out.println("登录成功！欢迎您，" + loginUser.getName() + "（" + loginUser.getRole() + "）");

        // 6. 根据角色跳转菜单
        switch (loginUser.getRole()) {
            case "ADMIN":
                new AdminController().manage();
                break;
            case "TEACHER":
                new TeacherController().manage();
                break;
            case "STUDENT":
                new StudentController().manage();
                break;
            default:
                System.out.println("未知角色：" + loginUser.getRole());
        }
    }
}
