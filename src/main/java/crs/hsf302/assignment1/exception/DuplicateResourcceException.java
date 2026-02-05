package crs.hsf302.assignment1.exception;

import com.sun.jdi.request.DuplicateRequestException;

public class DuplicateResourcceException extends DuplicateRequestException {
    public DuplicateResourcceException(String message) {
        super(message);
    }

    public DuplicateResourcceException() {
    }
}
