package entities;

import java.util.ArrayList;
import java.util.List;


public class Instructor extends Person {

    private String department;
    private List<Course> assignedCourses;

    public Instructor(String instructorId, String firstName, String lastName,
                      String email, String department) {
        super(instructorId, firstName, lastName, email);
        this.department = department;
        this.assignedCourses = new ArrayList<>();
    }

    @Override
    public String getRole() {
        return "Instructor";
    }

    @Override
    public void displayInfo() {
        System.out.println("========== Instructor Profile ==========");
        System.out.println("  ID         : " + getId());
        System.out.println("  Name       : " + getFullName());
        System.out.println("  Email      : " + getEmail());
        System.out.println("  Department : " + department);
        System.out.println("  Courses    : " + assignedCourses.size());
        System.out.println("========================================");
    }

    public void assignCourse(Course course) {
        if (!assignedCourses.contains(course)) {
            assignedCourses.add(course);
        }
    }

    public String getDepartment() { return department; }
    public List<Course> getAssignedCourses() { return assignedCourses; }
}