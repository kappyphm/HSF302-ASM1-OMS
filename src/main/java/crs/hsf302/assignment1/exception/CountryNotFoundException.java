package crs.hsf302.assignment1.exception;

public class CountryNotFoundException extends ResourceNotFoundException {
    public CountryNotFoundException(String s) {
        super(s);
    }
}
