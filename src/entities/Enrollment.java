package entities;

import java.time.LocalDate;


public class Enrollment {

    private Student student;
    private Course course;
    private LocalDate enrollmentDate;
    private Double grade;  // null = not graded yet
    private String status;  // ACTIVE, COMPLETED, DROPPED

    public Enrollment(Student student, Course course) {
        this.student = student;
        this.course = course;
        this.enrollmentDate = LocalDate.now();
        this.grade = null;
        this.status = "ACTIVE";
    }

    public void assignGrade(double grade) {
        this.grade = grade;
        this.status = "COMPLETED";
    }

    public boolean isGraded() {
        return grade != null;
    }



    public String getLetterGrade() {
        if (!isGraded()) return "N/A";

        // GPA scale: 0.0 - 5.0
        if (grade >= 4.5) return "A";
        if (grade >= 4.0) return "A-";
        if (grade >= 3.5) return "B+";
        if (grade >= 3.0) return "B";
        if (grade >= 2.5) return "B-";
        if (grade >= 2.0) return "C+";
        if (grade >= 1.5) return "C";
        if (grade >= 1.0) return "C-";
        if (grade >= 0.5) return "D";
        return "F";
    }


    public Student getStudent() { return student; }
    public Course getCourse() { return course; }
    public LocalDate getEnrollmentDate() { return enrollmentDate; }
    public Double getGrade() { return grade; }
    public String getStatus() { return status; }
}