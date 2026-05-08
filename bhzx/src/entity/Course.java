package entity;

import java.util.Date;

public class Course {
    private Long id;
    private String courseNo;
    private String courseName;
    private Integer credit;
    private Integer hours;
    private String description;
    private Long teacherId;
    private String teacherName; // 关联查询教师姓名
    private Integer maxStudent;
    private String status;
    private Date createTime;

    public Course() {}

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getCourseNo() { return courseNo; }
    public void setCourseNo(String courseNo) { this.courseNo = courseNo; }
    
    public String getCourseName() { return courseName; }
    public void setCourseName(String courseName) { this.courseName = courseName; }
    
    public Integer getCredit() { return credit; }
    public void setCredit(Integer credit) { this.credit = credit; }
    
    public Integer getHours() { return hours; }
    public void setHours(Integer hours) { this.hours = hours; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    public Long getTeacherId() { return teacherId; }
    public void setTeacherId(Long teacherId) { this.teacherId = teacherId; }
    
    public String getTeacherName() { return teacherName; }
    public void setTeacherName(String teacherName) { this.teacherName = teacherName; }
    
    public Integer getMaxStudent() { return maxStudent; }
    public void setMaxStudent(Integer maxStudent) { this.maxStudent = maxStudent; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    
    public Date getCreateTime() { return createTime; }
    public void setCreateTime(Date createTime) { this.createTime = createTime; }

    @Override
    public String toString() {
        return String.format("| %-2d | %-10s | %-20s | %-4d | %-4d | %-15s | %-30s |",
                id, courseNo, courseName, credit, hours, 
                teacherName != null ? teacherName : "未分配", status);
    }
}
