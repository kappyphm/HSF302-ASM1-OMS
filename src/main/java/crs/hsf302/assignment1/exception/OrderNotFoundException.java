package crs.hsf302.assignment1.exception;

public class OrderNotFoundException extends ResourceNotFoundException {
    public OrderNotFoundException(String string) {
        super(string);
    }
}
