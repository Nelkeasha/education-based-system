package entities;

import java.util.ArrayList;
import java.util.List;

public class Student extends Person {

    private String major;
    private double gpa;
    private List<Enrollment> enrollments;

    public Student(String studentId, String firstName, String lastName,
                   String email, String major) {
        super(studentId, firstName, lastName, email);
        this.major = major;
        this.gpa = 0.0;
        this.enrollments = new ArrayList<>();
    }

    @Override
    public String getRole() {
        return "Student";
    }

    @Override
    public void displayInfo() {
        System.out.println("========== Student Profile ==========");
        System.out.println("  ID    : " + getId());
        System.out.println("  Name  : " + getFullName());
        System.out.println("  Email : " + getEmail());
        System.out.println("  Major : " + major);
        System.out.printf ("  GPA   : %.2f%n", gpa);
        System.out.println("  Enrolled Courses: " + enrollments.size());
        System.out.println("=====================================");
    }

    public void addEnrollment(Enrollment enrollment) {
        enrollments.add(enrollment);
        recalculateGPA();
    }

    public void recalculateGPA() {
        if (enrollments.isEmpty()) {
            this.gpa = 0.0;
            return;
        }

        double totalPoints = 0.0;
        int gradedCourses = 0;

        for (int i = 0; i < enrollments.size(); i++) {
            Enrollment e = enrollments.get(i);
            if (e.isGraded()) {
                totalPoints += e.getGrade();
                gradedCourses++;
            }
        }

        this.gpa = (gradedCourses > 0) ? totalPoints / gradedCourses : 0.0;
    }

    public void displayAcademicSummary() {
        System.out.println("\n--- Academic Summary for " + getFullName() + " ---");
        System.out.printf("Current GPA: %.2f%n", gpa);
        System.out.println("Courses Enrolled: " + enrollments.size());

        if (!enrollments.isEmpty()) {
            System.out.println("\nCourse Details:");
            for (int i = 0; i < enrollments.size(); i++) {
                Enrollment e = enrollments.get(i);
                System.out.printf("  %d. %s - %s | Grade: %s%n",
                        i + 1,
                        e.getCourse().getCourseCode(),
                        e.getCourse().getCourseName(),
                        e.isGraded() ? String.format("%.2f", e.getGrade()) : "Not Graded"
                );
            }
        }
    }

    public String getMajor() { return major; }
    public double getGpa() { return gpa; }
    public List<Enrollment> getEnrollments() { return enrollments; }
}