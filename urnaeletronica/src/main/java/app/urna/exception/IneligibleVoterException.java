package app.urna.exception;

public class IneligibleVoterException extends RuntimeException {
    public IneligibleVoterException(String message) {
        super(message);
    }
}
