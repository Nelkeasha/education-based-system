package services;

import entities.*;
import exceptions.*;

import java.util.*;


public class EnrollmentService {

    private StudentService studentService;
    private CourseService courseService;
    private List<Enrollment> enrollments;  // Collections usage

    public EnrollmentService(StudentService studentService, CourseService courseService) {
        this.studentService = studentService;
        this.courseService = courseService;
        this.enrollments = new ArrayList<>();
    }


    public void enrollStudent(String studentId, String courseCode) {

        Student student = studentService.getStudent(studentId);
        Course course = courseService.getCourse(courseCode);

        // Validation 3: Check for duplicate enrollment
        for (int i = 0; i < enrollments.size(); i++) {
            Enrollment e = enrollments.get(i);
            if (e.getStudent().getId().equals(studentId) &&
                    e.getCourse().getCourseCode().equals(courseCode)) {
                throw new DuplicateEnrollmentException(studentId, courseCode);
            }
        }


        if (course.isFull()) {
            throw new UniversitySystemException(
                    "Cannot enroll: Course '" + courseCode + "' is full",
                    "COURSE_FULL"
            );
        }


        Enrollment enrollment = new Enrollment(student, course);
        enrollments.add(enrollment);
        student.addEnrollment(enrollment);
        course.addEnrollment(enrollment);

        System.out.println("✓ Enrolled: " + student.getFullName() +
                " in " + course.getCourseName());
    }


    public void assignGrade(String studentId, String courseCode, double grade) {

        if (grade < 0.0 || grade > 5.0) {
            throw new InvalidGradeException(grade);
        }


        Enrollment enrollment = findEnrollment(studentId, courseCode);
        if (enrollment == null) {
            throw new NotEnrolledException(studentId, courseCode);
        }

        enrollment.assignGrade(grade);
        enrollment.getStudent().recalculateGPA();

        System.out.printf("✓ Grade assigned: %.2f (%s) for %s in %s%n",
                grade,
                enrollment.getLetterGrade(),
                enrollment.getStudent().getFullName(),
                enrollment.getCourse().getCourseName()
        );
    }


    private Enrollment findEnrollment(String studentId, String courseCode) {
        for (int i = 0; i < enrollments.size(); i++) {
            Enrollment e = enrollments.get(i);
            if (e.getStudent().getId().equals(studentId) &&
                    e.getCourse().getCourseCode().equals(courseCode)) {
                return e;
            }
        }
        return null;
    }


    public List<Enrollment> getEnrollmentsByStudent(String studentId) {
        List<Enrollment> result = new ArrayList<>();
        for (int i = 0; i < enrollments.size(); i++) {
            Enrollment e = enrollments.get(i);
            if (e.getStudent().getId().equals(studentId)) {
                result.add(e);
            }
        }
        return result;
    }


    public List<Enrollment> getEnrollmentsByCourse(String courseCode) {
        List<Enrollment> result = new ArrayList<>();
        for (int i = 0; i < enrollments.size(); i++) {
            Enrollment e = enrollments.get(i);
            if (e.getCourse().getCourseCode().equals(courseCode)) {
                result.add(e);
            }
        }
        return result;
    }

    public List<Enrollment> getAllEnrollments() {
        return new ArrayList<>(enrollments);
    }
}