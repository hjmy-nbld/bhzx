package dao;

import entity.Course;
import util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CourseDAO {
    
    /**
     * 获取所有课程（包含教师信息）
     */
    public List<Course> getAllCourses() {
        List<Course> courses = new ArrayList<>();
        String sql = "SELECT c.*, u.name as teacher_name FROM course c " +
                     "LEFT JOIN user u ON c.teacher_id = u.id ORDER BY c.id";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                courses.add(mapRowToCourse(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return courses;
    }
    
    /**
     * 根据ID查询课程
     */
    public Course getCourseById(Long id) {
        String sql = "SELECT c.*, u.name as teacher_name FROM course c " +
                     "LEFT JOIN user u ON c.teacher_id = u.id WHERE c.id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapRowToCourse(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    /**
     * 根据教师ID查询课程
     */
    public List<Course> getCoursesByTeacherId(Long teacherId) {
        List<Course> courses = new ArrayList<>();
        String sql = "SELECT c.*, u.name as teacher_name FROM course c " +
                     "LEFT JOIN user u ON c.teacher_id = u.id WHERE c.teacher_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, teacherId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    courses.add(mapRowToCourse(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return courses;
    }
    
    /**
     * 添加课程
     */
    public boolean addCourse(Course course) {
        String sql = "INSERT INTO course (course_no, course_name, credit, hours, description, teacher_id, max_student) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, course.getCourseNo());
            pstmt.setString(2, course.getCourseName());
            pstmt.setInt(3, course.getCredit());
            pstmt.setInt(4, course.getHours());
            pstmt.setString(5, course.getDescription());
            pstmt.setObject(6, course.getTeacherId());
            pstmt.setInt(7, course.getMaxStudent());
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * 更新课程
     */
    public boolean updateCourse(Course course) {
        String sql = "UPDATE course SET course_no=?, course_name=?, credit=?, hours=?, description=?, teacher_id=?, max_student=? WHERE id=?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, course.getCourseNo());
            pstmt.setString(2, course.getCourseName());
            pstmt.setInt(3, course.getCredit());
            pstmt.setInt(4, course.getHours());
            pstmt.setString(5, course.getDescription());
            pstmt.setObject(6, course.getTeacherId());
            pstmt.setInt(7, course.getMaxStudent());
            pstmt.setLong(8, course.getId());
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * 删除课程
     */
    public boolean deleteCourse(Long id) {
        String sql = "DELETE FROM course WHERE id = ?";
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
     * 搜索课程
     */
    public List<Course> searchCourses(String keyword) {
        List<Course> courses = new ArrayList<>();
        String sql = "SELECT c.*, u.name as teacher_name FROM course c " +
                     "LEFT JOIN user u ON c.teacher_id = u.id " +
                     "WHERE c.course_no LIKE ? OR c.course_name LIKE ? OR c.description LIKE ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            String pattern = "%" + keyword + "%";
            pstmt.setString(1, pattern);
            pstmt.setString(2, pattern);
            pstmt.setString(3, pattern);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    courses.add(mapRowToCourse(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return courses;
    }
    
    /**
     * 将ResultSet行映射为Course对象
     */
    private Course mapRowToCourse(ResultSet rs) throws SQLException {
        Course course = new Course();
        course.setId(rs.getLong("id"));
        course.setCourseNo(rs.getString("course_no"));
        course.setCourseName(rs.getString("course_name"));
        course.setCredit(rs.getInt("credit"));
        course.setHours(rs.getInt("hours"));
        course.setDescription(rs.getString("description"));
        course.setTeacherId(rs.getLong("teacher_id"));
        course.setTeacherName(rs.getString("teacher_name"));
        course.setMaxStudent(rs.getInt("max_student"));
        course.setStatus(rs.getString("status"));
        course.setCreateTime(rs.getTimestamp("create_time"));
        return course;
    }
}
