package exceptions;

public class NotEnrolledException extends UniversitySystemException {

  private String studentId;
  private String courseCode;

  public NotEnrolledException(String studentId, String courseCode) {
    super(
            "Not enrolled: Student '" + studentId +
                    "' is not enrolled in course '" + courseCode + "'",
            "NOT_ENROLLED"
    );
    this.studentId = studentId;
    this.courseCode = courseCode;
  }

  public String getStudentId() {
    return studentId;
  }

  public String getCourseCode() {
    return courseCode;
  }
}