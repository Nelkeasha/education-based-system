package services;

import entities.Course;
import exceptions.CourseNotFoundException;

import java.util.List;
import java.util.Optional;


public class CourseService {

    private GenericRepository<Course> courseRepo;

    public CourseService() {
        this.courseRepo = new GenericRepository<>(Course::getCourseCode);
    }

    public void registerCourse(Course course) {
        if (courseRepo.exists(course.getCourseCode())) {
            throw new UniversitySystemException(
                    "Course with code '" + course.getCourseCode() + "' already exists",
                    "DUPLICATE_COURSE"
            );
        }
        courseRepo.add(course);
        System.out.println("✓ Course registered: " + course.getCourseName());
    }

    public Course getCourse(String courseCode) {
        Optional<Course> course = courseRepo.findById(courseCode);
        if (!course.isPresent()) {
            throw new CourseNotFoundException(courseCode);
        }
        return course.get();
    }

    public List<Course> getAllCourses() {
        return courseRepo.findAll();
    }


    public List<Course> getAvailableCourses() {
        return courseRepo.findWhere(c -> !c.isFull());
    }

    public int getTotalCourses() {
        return courseRepo.count();
    }
}