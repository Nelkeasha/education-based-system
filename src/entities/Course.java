package entities;

import java.util.ArrayList;
import java.util.List;


public class Course {

    private String courseCode;
    private String courseName;
    private int creditHours;
    private int maxCapacity;
    private Instructor instructor;
    private List<Enrollment> enrollments;

    public Course(String courseCode, String courseName, int creditHours,
                  int maxCapacity) {
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.creditHours = creditHours;
        this.maxCapacity = maxCapacity;
        this.enrollments = new ArrayList<>();
    }

    public void assignInstructor(Instructor instructor) {
        this.instructor = instructor;
        instructor.assignCourse(this);
    }

    public void addEnrollment(Enrollment enrollment) {
        enrollments.add(enrollment);
    }

    public boolean isFull() {
        return enrollments.size() >= maxCapacity;
    }

    public int getAvailableSeats() {
        return maxCapacity - enrollments.size();
    }

    public void displayCourseInfo() {
        System.out.println("========== Course Info ==========");
        System.out.println("  Code       : " + courseCode);
        System.out.println("  Name       : " + courseName);
        System.out.println("  Credits    : " + creditHours);
        System.out.println("  Instructor : " + (instructor != null ? instructor.getFullName() : "TBA"));
        System.out.println("  Capacity   : " + enrollments.size() + "/" + maxCapacity);
        System.out.println("  Status     : " + (isFull() ? "FULL" : "OPEN"));
        System.out.println("=================================");
    }


    public String getCourseCode() { return courseCode; }
    public String getCourseName() { return courseName; }
    public int getCreditHours() { return creditHours; }
    public int getMaxCapacity() { return maxCapacity; }
    public Instructor getInstructor() { return instructor; }
    public List<Enrollment> getEnrollments() { return enrollments; }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Course course = (Course) obj;
        return courseCode.equals(course.courseCode);
    }

    @Override
    public int hashCode() {
        return courseCode.hashCode();
    }
}