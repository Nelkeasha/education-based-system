import entities.*;
import exceptions.*;
import services.*;
import java.util.Scanner;
import java.util.List;

public class Main {

    private static StudentService studentService;
    private static CourseService courseService;
    private static EnrollmentService enrollmentService;
    private static Scanner scanner;

    public static void main(String[] args) {

        studentService = new StudentService();
        courseService = new CourseService();
        enrollmentService = new EnrollmentService(studentService, courseService);
        scanner = new Scanner(System.in);

        System.out.println("================================================");
        System.out.println("  Education based system                       ");
        System.out.println("================================================\n");

        boolean running = true;
        while (running) {
            displayMainMenu();
            int choice = getIntInput("Enter your choice: ", 0, 11);
            System.out.println();

            switch (choice) {
                case 1:  registerStudent(); break;
                case 2:  registerCourse(); break;
                case 3:  enrollStudentInCourse(); break;
                case 4:  assignGradeToStudent(); break;
                case 5:  viewAllStudents(); break;
                case 6:  viewAllCourses(); break;
                case 7:  viewStudentDetails(); break;
                case 8:  viewCourseDetails(); break;
                case 9:  viewAcademicSummary(); break;
                case 10: viewSystemStatistics(); break;
                case 0:
                    running = false;
                    System.out.println("Thank you for using the University Enrollment System!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }

            if (running) {
                System.out.println("\nPress Enter to continue...");
                scanner.nextLine();
            }
        }
        scanner.close();
    }

    // ================== MENU ==================
    private static void displayMainMenu() {
        System.out.println("\n===========================================");
        System.out.println("║            MAIN MENU                       ║");
        System.out.println("==============================================");
        System.out.println("  1. Register Student");
        System.out.println("  2. Register Course");
        System.out.println("  3. Enroll Student in Course");
        System.out.println("  4. Assign Grade to Student (0.0 - 5.0)");
        System.out.println("  5. View All Students");
        System.out.println("  6. View All Courses");
        System.out.println("  7. View Student Details");
        System.out.println("  8. View Course Details");
        System.out.println("  9. View Student Academic Summary");
        System.out.println(" 10. View System Statistics");
        System.out.println("  0. Exit");
        System.out.println("=========================================");
    }

    // ================== 1. REGISTER STUDENT ==================
    private static void registerStudent() {
        System.out.println("=========================================");
        System.out.println("        REGISTER NEW STUDENT             ");
        System.out.println("=========================================\n");

        try {
            String studentId = getNonEmptyInput("Enter Student ID (e.g., S001): ");
            String firstName = getNameInput("Enter First Name: ");
            String lastName  = getNameInput("Enter Last Name: ");
            String email     = getEmailInput("Enter Email: ");
            String major     = getNonEmptyInput("Enter Major: ");

            Student student = new Student(studentId, firstName, lastName, email, major);
            studentService.registerStudent(student);

            System.out.println("\n✓ SUCCESS: Student registered successfully!");
            student.displayInfo();

        } catch (UniversitySystemException e) {
            System.out.println("\n✗ ERROR: " + e.getMessage());
            System.out.println("  Error Code: " + e.getErrorCode());
        } catch (Exception e) {
            System.out.println("\n✗ UNEXPECTED ERROR: " + e.getMessage());
        }
    }

    // ================== 2. REGISTER COURSE ==================
    private static void registerCourse() {
        System.out.println("=========================================");
        System.out.println("║          REGISTER NEW COURSE               ║");
        System.out.println("=========================================\n");

        try {
            String courseCode = getNonEmptyInput("Enter Course Code (e.g., CS101): ");
            String courseName = getNonEmptyInput("Enter Course Name: ");
            int creditHours   = getIntInput("Enter Credit Hours (1-6): ", 1, 6);
            int maxCapacity   = getIntInput("Enter Maximum Capacity (1-100): ", 1, 100);

            Course course = new Course(courseCode, courseName, creditHours, maxCapacity);
            courseService.registerCourse(course);

            System.out.println("\n✓ SUCCESS: Course registered successfully!");
            course.displayCourseInfo();

        } catch (UniversitySystemException e) {
            System.out.println("\n✗ ERROR: " + e.getMessage());
            System.out.println("  Error Code: " + e.getErrorCode());
        } catch (Exception e) {
            System.out.println("\n✗ UNEXPECTED ERROR: " + e.getMessage());
        }
    }

    // ================== 3. ENROLL STUDENT ==================
    private static void enrollStudentInCourse() {
        System.out.println("=========================================");
        System.out.println("║       ENROLL STUDENT IN COURSE             ║");
        System.out.println("=========================================\n");

        viewAllStudents();
        System.out.println();
        viewAllCourses();
        System.out.println();

        try {
            String studentId  = getNonEmptyInput("Enter Student ID: ");
            String courseCode = getNonEmptyInput("Enter Course Code: ");

            enrollmentService.enrollStudent(studentId, courseCode);
            System.out.println("\n✓ SUCCESS: Student enrolled successfully!");

        } catch (DuplicateEnrollmentException e) {
            System.out.println("\n✗ DUPLICATE ENROLLMENT");
            System.out.println("  Message     : " + e.getMessage());
            System.out.println("  Error Code  : " + e.getErrorCode());
            System.out.println("  Student ID  : " + e.getStudentId());
            System.out.println("  Course Code : " + e.getCourseCode());
        } catch (StudentNotFoundException e) {
            System.out.println("\n✗ STUDENT NOT FOUND");
            System.out.println("  Message    : " + e.getMessage());
            System.out.println("  Student ID : " + e.getStudentId());
        } catch (CourseNotFoundException e) {
            System.out.println("\n✗ COURSE NOT FOUND");
            System.out.println("  Message     : " + e.getMessage());
            System.out.println("  Course Code : " + e.getCourseCode());
        } catch (UniversitySystemException e) {
            System.out.println("\n✗ ERROR: " + e.getMessage());
            System.out.println("  Error Code: " + e.getErrorCode());
        } catch (Exception e) {
            System.out.println("\n✗ UNEXPECTED ERROR: " + e.getMessage());
        }
    }

    // ================== 4. ASSIGN GRADE (0.0 - 5.0) ==================
    private static void assignGradeToStudent() {
        System.out.println("=========================================");
        System.out.println("║         ASSIGN GRADE TO STUDENT            ║");
        System.out.println("║         (Scale: 0.0 - 5.0)                 ║");
        System.out.println("=============================================\n");

        List<Enrollment> enrollments = enrollmentService.getAllEnrollments();
        if (enrollments.isEmpty()) {
            System.out.println("No enrollments found. Please enroll students first.");
            return;
        }

        System.out.println("Current Enrollments:");
        for (int i = 0; i < enrollments.size(); i++) {
            Enrollment e = enrollments.get(i);
            System.out.printf("  %d. Student: %s | Course: %s | Status: %s | Grade: %s%n",
                    i + 1,
                    e.getStudent().getId() + " - " + e.getStudent().getFullName(),
                    e.getCourse().getCourseCode() + " - " + e.getCourse().getCourseName(),
                    e.getStatus(),
                    e.isGraded() ? String.format("%.2f", e.getGrade()) : "Not Graded"
            );
        }
        System.out.println();

        try {
            String studentId  = getNonEmptyInput("Enter Student ID: ");
            String courseCode = getNonEmptyInput("Enter Course Code: ");
            double grade      = getDoubleInput("Enter Grade (0.0 - 5.0): ", 0.0, 5.0);

            enrollmentService.assignGrade(studentId, courseCode, grade);
            System.out.println("\n✓ SUCCESS: Grade assigned successfully!");

        } catch (InvalidGradeException e) {
            System.out.println("\n✗ INVALID GRADE");
            System.out.println("  Message         : " + e.getMessage());
            System.out.println("  Error Code      : " + e.getErrorCode());
            System.out.println("  Attempted Grade : " + e.getAttemptedGrade());
        } catch (NotEnrolledException e) {
            System.out.println("\n✗ NOT ENROLLED");
            System.out.println("  Message     : " + e.getMessage());
            System.out.println("  Student ID  : " + e.getStudentId());
            System.out.println("  Course Code : " + e.getCourseCode());
        } catch (StudentNotFoundException e) {
            System.out.println("\n✗ STUDENT NOT FOUND");
            System.out.println("  Message    : " + e.getMessage());
            System.out.println("  Student ID : " + e.getStudentId());
        } catch (UniversitySystemException e) {
            System.out.println("\n✗ ERROR: " + e.getMessage());
            System.out.println("  Error Code: " + e.getErrorCode());
        } catch (Exception e) {
            System.out.println("\n✗ UNEXPECTED ERROR: " + e.getMessage());
        }
    }

    // ================== 5. VIEW ALL STUDENTS ==================
    private static void viewAllStudents() {
        System.out.println("=============================================");
        System.out.println("║          ALL REGISTERED STUDENTS           ║");
        System.out.println("=============================================\n");

        List<Student> students = studentService.getAllStudents();
        if (students.isEmpty()) {
            System.out.println("No students registered yet.");
        } else {
            for (int i = 0; i < students.size(); i++) {
                Student s = students.get(i);
                System.out.printf("  %d. ID: %s | Name: %s | Major: %s | GPA: %.2f / 5.0%n",
                        i + 1, s.getId(), s.getFullName(), s.getMajor(), s.getGpa());
            }
            System.out.println("\nTotal Students: " + students.size());
        }
    }

    // ================== 6. VIEW ALL COURSES ==================
    private static void viewAllCourses() {
        System.out.println("=========================================");
        System.out.println("║          ALL REGISTERED COURSES            ║");
        System.out.println("============================================\n");

        List<Course> courses = courseService.getAllCourses();
        if (courses.isEmpty()) {
            System.out.println("No courses registered yet.");
        } else {
            for (int i = 0; i < courses.size(); i++) {
                Course c = courses.get(i);
                System.out.printf("  %d. Code: %s | Name: %s | Credits: %d | Enrolled: %d/%d | Status: %s%n",
                        i + 1, c.getCourseCode(), c.getCourseName(), c.getCreditHours(),
                        c.getEnrollments().size(), c.getMaxCapacity(),
                        c.isFull() ? "FULL" : "OPEN");
            }
            System.out.println("\nTotal Courses: " + courses.size());
        }
    }

    // ================== 7. VIEW STUDENT DETAILS ==================
    private static void viewStudentDetails() {
        System.out.println("=============================================");
        System.out.println("║           VIEW STUDENT DETAILS             ║");
        System.out.println("=============================================\n");

        try {
            String studentId = getNonEmptyInput("Enter Student ID: ");
            Student student = studentService.getStudent(studentId);
            student.displayInfo();

            System.out.println("\nEnrolled Courses:");
            List<Enrollment> enrollments = student.getEnrollments();
            if (enrollments.isEmpty()) {
                System.out.println("  No courses enrolled.");
            } else {
                for (int i = 0; i < enrollments.size(); i++) {
                    Enrollment e = enrollments.get(i);
                    System.out.printf("  %d. %s - %s | Grade: %s | Status: %s%n",
                            i + 1,
                            e.getCourse().getCourseCode(),
                            e.getCourse().getCourseName(),
                            e.isGraded() ? String.format("%.2f (%s)", e.getGrade(), e.getLetterGrade()) : "Not Graded",
                            e.getStatus());
                }
            }

        } catch (StudentNotFoundException e) {
            System.out.println("\n✗ STUDENT NOT FOUND");
            System.out.println("  Message    : " + e.getMessage());
            System.out.println("  Student ID : " + e.getStudentId());
        }
    }

    // ================== 8. VIEW COURSE DETAILS ==================
    private static void viewCourseDetails() {
        System.out.println("=============================================");
        System.out.println("║           VIEW COURSE DETAILS              ║");
        System.out.println("=============================================\n");

        try {
            String courseCode = getNonEmptyInput("Enter Course Code: ");
            Course course = courseService.getCourse(courseCode);
            course.displayCourseInfo();

            System.out.println("\nEnrolled Students:");
            List<Enrollment> enrollments = course.getEnrollments();
            if (enrollments.isEmpty()) {
                System.out.println("  No students enrolled.");
            } else {
                for (int i = 0; i < enrollments.size(); i++) {
                    Enrollment e = enrollments.get(i);
                    System.out.printf("  %d. %s - %s | Grade: %s%n",
                            i + 1,
                            e.getStudent().getId(),
                            e.getStudent().getFullName(),
                            e.isGraded() ? String.format("%.2f (%s)", e.getGrade(), e.getLetterGrade()) : "Not Graded");
                }
            }

        } catch (CourseNotFoundException e) {
            System.out.println("\n✗ COURSE NOT FOUND");
            System.out.println("  Message     : " + e.getMessage());
            System.out.println("  Course Code : " + e.getCourseCode());
        }
    }

    // ================== 9. ACADEMIC SUMMARY ==================
    private static void viewAcademicSummary() {
        System.out.println("=========================================");
        System.out.println("║       STUDENT ACADEMIC SUMMARY         ║");
        System.out.println("=========================================\n");

        try {
            String studentId = getNonEmptyInput("Enter Student ID: ");
            Student student = studentService.getStudent(studentId);
            student.displayAcademicSummary();

        } catch (StudentNotFoundException e) {
            System.out.println("\n✗ STUDENT NOT FOUND");
            System.out.println("  Message    : " + e.getMessage());
            System.out.println("  Student ID : " + e.getStudentId());
        }
    }

    // ================== 10. SYSTEM STATISTICS (with High Performers) ==================
    private static void viewSystemStatistics() {
        System.out.println("=========================================");
        System.out.println("║         SYSTEM STATISTICS             ║");
        System.out.println("║         (GPA Scale: 0.0 - 5.0)        ║");
        System.out.println("=========================================\n");

        int totalStudents    = studentService.getTotalStudents();
        int totalCourses     = courseService.getTotalCourses();
        int totalEnrollments = enrollmentService.getAllEnrollments().size();

        System.out.println("  Total Students     : " + totalStudents);
        System.out.println("  Total Courses      : " + totalCourses);
        System.out.println("  Total Enrollments  : " + totalEnrollments);

        List<Course> availableCourses = courseService.getAvailableCourses();
        System.out.println("  Available Courses  : " + availableCourses.size());

        // High performers: GPA >= 3.5 on 5.0 scale
        double highPerformerThreshold = 3.5;
        List<Student> highPerformers = studentService.getHighPerformers(highPerformerThreshold);
        System.out.println("  High Performers (GPA ≥ " + highPerformerThreshold + ") : " + highPerformers.size());

        if (!highPerformers.isEmpty()) {
            System.out.println("\n  =========================================");
            System.out.println("  ║      HIGH PERFORMERS LIST               ║");
            System.out.println("  ===========================================");
            for (int i = 0; i < highPerformers.size(); i++) {
                Student s = highPerformers.get(i);
                System.out.printf("    %d. %s (ID: %s) | GPA: %.2f / 5.0 | Major: %s%n",
                        i + 1, s.getFullName(), s.getId(), s.getGpa(), s.getMajor());
            }
        } else {
            System.out.println("\n  No high performers yet. Keep grading!");
        }
    }

    // ================== INPUT VALIDATION HELPERS ==================

    private static String getNonEmptyInput(String prompt) {
        String input = "";
        while (input.trim().isEmpty()) {
            System.out.print(prompt);
            input = scanner.nextLine().trim();
            if (input.isEmpty()) {
                System.out.println("✗ Input cannot be empty. Please try again.");
            }
        }
        return input;
    }

    private static String getNameInput(String prompt) {
        String input = "";
        boolean valid = false;
        while (!valid) {
            System.out.print(prompt);
            input = scanner.nextLine().trim();
            if (input.isEmpty()) {
                System.out.println("✗ Name cannot be empty.");
            } else if (!input.matches("^[a-zA-Z\\s-]+$")) {
                System.out.println("✗ Only letters, spaces, and hyphens allowed.");
            } else {
                valid = true;
            }
        }
        return input;
    }

    private static String getEmailInput(String prompt) {
        String input = "";
        boolean valid = false;
        while (!valid) {
            System.out.print(prompt);
            input = scanner.nextLine().trim();
            if (input.isEmpty()) {
                System.out.println("✗ Email cannot be empty.");
            } else if (!input.contains("@") || !input.contains(".")) {
                System.out.println("✗ Invalid email. Must contain '@' and '.'");
            } else if (input.indexOf("@") > input.lastIndexOf(".")) {
                System.out.println("✗ Domain must come after '@'.");
            } else {
                valid = true;
            }
        }
        return input;
    }

    private static int getIntInput(String prompt, int min, int max) {
        int input = 0;
        boolean valid = false;
        while (!valid) {
            System.out.print(prompt);
            try {
                String line = scanner.nextLine().trim();
                input = Integer.parseInt(line);
                if (input < min || input > max) {
                    System.out.printf("✗ Input must be between %d and %d.%n", min, max);
                } else {
                    valid = true;
                }
            } catch (NumberFormatException e) {
                System.out.println("✗ Invalid input. Please enter a number.");
            }
        }
        return input;
    }

    private static double getDoubleInput(String prompt, double min, double max) {
        double input = 0.0;
        boolean valid = false;
        while (!valid) {
            System.out.print(prompt);
            try {
                String line = scanner.nextLine().trim();
                input = Double.parseDouble(line);
                if (input < min || input > max) {
                    System.out.printf("✗ Input must be between %.2f and %.2f.%n", min, max);
                } else {
                    valid = true;
                }
            } catch (NumberFormatException e) {
                System.out.println("✗ Invalid input. Please enter a number.");
            }
        }
        return input;
    }
}