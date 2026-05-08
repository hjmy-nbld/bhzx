package dao;

import entity.User;
import util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {


     /**
     * 用户登录（使用 ID 和密码）
     */
    public User login(Long id, String password) {
        String sql = "SELECT * FROM user WHERE id = ? AND password = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, id);
            pstmt.setString(2, password);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapRowToUser(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 根据ID查询用户
     */
    public User getUserById(Long id) {
        String sql = "SELECT * FROM user WHERE id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapRowToUser(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 根据用户名查询用户
     */
    public User getUserByUsername(String username) {
        String sql = "SELECT * FROM user WHERE username = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapRowToUser(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取所有用户
     */
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM user ORDER BY id";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                users.add(mapRowToUser(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    /**
     * 根据角色获取用户列表
     */
    public List<User> getUsersByRole(String role) {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM user WHERE role = ? ORDER BY id";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, role);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    users.add(mapRowToUser(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    /**
     * 添加用户
     */
    public boolean addUser(User user) {
        String sql = "INSERT INTO user (username, password, name, role, gender) " +
                     "VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, user.getUsername());
            pstmt.setString(2, user.getPassword());
            pstmt.setString(3, user.getName());
            pstmt.setString(4, user.getRole());
            pstmt.setString(5, user.getGender());
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 更新用户信息
     */
    public boolean updateUser(User user) {
        String sql = "UPDATE user SET name=?, gender=? WHERE id=?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, user.getName());
            pstmt.setString(2, user.getGender());
            pstmt.setLong(3, user.getId());
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 修改密码
     */
    public boolean updatePassword(Long userId, String newPassword) {
        String sql = "UPDATE user SET password=? WHERE id=?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, newPassword);
            pstmt.setLong(2, userId);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 删除用户
     */
    public boolean deleteUser(Long id) {
        String sql = "DELETE FROM user WHERE id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, id);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 搜索用户
     */
    public List<User> searchUsers(String keyword) {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM user WHERE username LIKE ? OR name LIKE ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            String pattern = "%" + keyword + "%";
            pstmt.setString(1, pattern);
            pstmt.setString(2, pattern);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    users.add(mapRowToUser(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }


    /**
     * 将ResultSet行映射为User对象
     */
    private User mapRowToUser(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(rs.getLong("id"));
        user.setUsername(rs.getString("username"));
        user.setPassword(rs.getString("password"));
        user.setName(rs.getString("name"));
        user.setRole(rs.getString("role"));
        user.setGender(rs.getString("gender"));
        user.setCreateTime(rs.getTimestamp("create_time"));
        user.setUpdateTime(rs.getTimestamp("update_time"));
        return user;
    }
}
