package exceptions;

public class InvalidGradeException extends UniversitySystemException {

    private double attemptedGrade;


    public InvalidGradeException(double attemptedGrade) {
        super(
                "Invalid grade: " + attemptedGrade +
                        ". Grade must be between 0.0 and 5.0",
                "INVALID_GRADE"
        );
        this.attemptedGrade = attemptedGrade;
    }

    public double getAttemptedGrade() { return attemptedGrade; }
}