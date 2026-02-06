package crs.hsf302.assignment1.domain.dto;

import java.util.UUID;

public record CustomerDto(
        UUID id,
        String firstName,
        String lastName,
        String email,
        String phone
) {
}
