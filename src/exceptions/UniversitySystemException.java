package exceptions;


public class UniversitySystemException extends RuntimeException {

    private String errorCode;

    public UniversitySystemException(String message) {
        super(message);
        this.errorCode = "UNI_ERROR";
    }

    public UniversitySystemException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {

        return errorCode;
    }

    @Override
    public String toString() {

        return "[" + errorCode + "] " + getMessage();
    }
}