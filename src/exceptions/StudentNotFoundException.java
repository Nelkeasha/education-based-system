package exceptions;

public class StudentNotFoundException extends UniversitySystemException {

    private String studentId;

    public StudentNotFoundException(String studentId) {
        super(
                "Student not found: No student with ID '" + studentId + "' exists",
                "STUDENT_NOT_FOUND"
        );
        this.studentId = studentId;
    }

    public String getStudentId() { return studentId; }
}