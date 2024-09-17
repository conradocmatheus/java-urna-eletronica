package app.urna.handler.exception;

public class IneligibleVoterException extends RuntimeException {
    public IneligibleVoterException(String message) {
        super(message);
    }
}
