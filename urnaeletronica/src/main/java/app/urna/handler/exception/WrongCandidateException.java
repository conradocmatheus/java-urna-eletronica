package app.urna.handler.exception;

public class WrongCandidateException extends RuntimeException {
    public WrongCandidateException(String message) {
        super(message);
    }
}
