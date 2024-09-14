package app.urna.exception;

public class WrongCandidateException extends RuntimeException {
    public WrongCandidateException(String message) {
        super(message);
    }
}
