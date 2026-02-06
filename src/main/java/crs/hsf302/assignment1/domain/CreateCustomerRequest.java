package crs.hsf302.assignment1.domain;

public record CreateCustomerRequest(
        String firstName,
        String lastName,
        String email,
        String phone
) {
}
