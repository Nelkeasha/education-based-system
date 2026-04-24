package services;

import entities.Student;
import exceptions.StudentNotFoundException;
import exceptions.UniversitySystemException;

import java.util.List;
import java.util.Optional;

public class StudentService {

    private GenericRepository<Student> studentRepo;

    public StudentService() {
        this.studentRepo = new GenericRepository<>(Student::getId);
    }

    public void registerStudent(Student student) {
        if (studentRepo.exists(student.getId())) {
            throw new UniversitySystemException(
                    "Student with ID '" + student.getId() + "' already exists",
                    "DUPLICATE_STUDENT"
            );
        }
        studentRepo.add(student);
        System.out.println("✓ Student registered: " + student.getFullName());
    }
    public Student getStudent(String studentId) {
        Optional<Student> student = studentRepo.findById(studentId);
        if (!student.isPresent()) {
            throw new StudentNotFoundException(studentId);
        }
        return student.get();
    }

    public List<Student> getAllStudents() {
        return studentRepo.findAll();
    }

    public List<Student> getStudentsByMajor(String major) {
        return studentRepo.findWhere(s -> s.getMajor().equalsIgnoreCase(major));
    }

    public List<Student> getHighPerformers(double gpaThreshold) {
        return studentRepo.findWhere(s -> s.getGpa() >= gpaThreshold);
    }

    public int getTotalStudents() {
        return studentRepo.count();
    }
}