package crs.hsf302.assignment1.exception;

public class ProgramNotFoundException extends ResourceNotFoundException {
    public ProgramNotFoundException(String message) {
        super(message);
    }
}
