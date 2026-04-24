package exceptions;


public class CourseNotFoundException extends UniversitySystemException {

    private String courseCode;

    public CourseNotFoundException(String courseCode) {
        super(
                "Course not found: No course with code '" + courseCode + "' exists",
                "COURSE_NOT_FOUND"
        );
        this.courseCode = courseCode;
    }

    public String getCourseCode() { return courseCode; }
}