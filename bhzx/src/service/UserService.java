package service;

import dao.UserDAO;
import entity.User;

import java.util.List;

public class UserService {
    private UserDAO userDAO = new UserDAO();
    
    /**
     * 检查用户是否存在
     */
    public boolean isUserExists(Long id) {
        return userDAO.getUserById(id) != null;
    }

    /**
     * 用户登录（使用 ID 和密码）
     */
    public User login(Long id, String password) {
        if (id == null || id <= 0) {
            return null;
        }
        if (password == null || password.trim().isEmpty()) {
            return null;
        }
        return userDAO.login(id, password.trim());
    }

    /**
     * 获取所有用户
     */
    public List<User> getAllUsers() {
        return userDAO.getAllUsers();
    }

    /**
     * 根据角色获取用户
     */
    public List<User> getUsersByRole(String role) {
        return userDAO.getUsersByRole(role);
    }

    /**
     * 添加用户
     */
    public String addUser(User user) {
        if (user.getUsername() == null || user.getUsername().trim().isEmpty()) {
            return "用户名不能为空";
        }
        if (userDAO.getUserByUsername(user.getUsername()) != null) {
            return "用户名已存在";
        }
        if (user.getPassword() == null || user.getPassword().length() < 6) {
            return "密码长度至少为 6 位";
        }
        if (!"ADMIN".equals(user.getRole()) &&
            !"TEACHER".equals(user.getRole()) &&
            !"STUDENT".equals(user.getRole())) {
            return "角色无效";
        }
        if (userDAO.addUser(user)) {
            return "添加成功";
        }
        return "添加失败";
    }

    /**
     * 更新用户信息
     */
    public String updateUser(User user) {
        if (user.getName() == null || user.getName().trim().isEmpty()) {
            return "姓名不能为空";
        }
        if (userDAO.updateUser(user)) {
            return "修改成功";
        }
        return "修改失败";
    }

    /**
     * 修改密码
     */
    public String updatePassword(Long userId, String oldPassword, String newPassword) {
        User user = userDAO.getUserById(userId);
        if (user == null) {
            return "用户不存在";
        }
        if (!oldPassword.equals(user.getPassword())) {
            return "原密码错误";
        }
        if (newPassword == null || newPassword.length() < 6) {
            return "新密码长度至少为 6 位";
        }
        if (userDAO.updatePassword(userId, newPassword)) {
            return "密码修改成功";
        }
        return "密码修改失败";
    }
    
    /**
     * 删除用户
     */
    public String deleteUser(Long id) {
        if (userDAO.deleteUser(id)) {
            return "删除成功";
        }
        return "删除失败";
    }
    
    /**
     * 搜索用户
     */
    public List<User> searchUsers(String keyword) {
        return userDAO.searchUsers(keyword);
    }
}
