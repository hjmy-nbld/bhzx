package service;

import dao.CourseDAO;
import entity.Course;

import java.util.List;

public class CourseService {
    private final CourseDAO courseDAO = new CourseDAO();
    
    public List<Course> getAllCourses() {
        return courseDAO.getAllCourses();
    }
    
    public Course getCourseById(Long id) {
        return courseDAO.getCourseById(id);
    }
    
    public List<Course> getCoursesByTeacherId(Long teacherId) {
        return courseDAO.getCoursesByTeacherId(teacherId);
    }
    
    public String addCourse(Course course) {
        if (course.getCourseNo() == null || course.getCourseNo().trim().isEmpty()) {
            return "课程编号不能为空";
        }
        if (course.getCourseName() == null || course.getCourseName().trim().isEmpty()) {
            return "课程名称不能为空";
        }
        if (courseDAO.addCourse(course)) {
            return "添加成功";
        }
        return "添加失败";
    }
    
    public String updateCourse(Course course) {
        if (courseDAO.updateCourse(course)) {
            return "修改成功";
        }
        return "修改失败";
    }
    
    public String deleteCourse(Long id) {
        if (courseDAO.deleteCourse(id)) {
            return "删除成功";
        }
        return "删除失败";
    }
    
    public List<Course> searchCourses(String keyword) {
        return courseDAO.searchCourses(keyword);
    }
}
