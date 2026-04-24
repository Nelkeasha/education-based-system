package exceptions;

public class DuplicateEnrollmentException extends UniversitySystemException {

    private String studentId;
    private String courseCode;

    public DuplicateEnrollmentException(String studentId, String courseCode) {
        super(
                "Duplicate enrollment detected: Student '" + studentId +
                        "' is already enrolled in course '" + courseCode + "'",
                "DUPLICATE_ENROLLMENT"
        );
        this.studentId = studentId;
        this.courseCode = courseCode;
    }

    public String getStudentId() { return studentId; }
    public String getCourseCode() { return courseCode; }
}