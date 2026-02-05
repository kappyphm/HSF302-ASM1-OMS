package crs.hsf302.assignment1.domain.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;

@Builder
public record CreateOrderDto(
        @NotBlank
        String firstName,
        @NotBlank
        String lastName,
        @NotBlank
        @Email
        String email,
        @NotBlank
        String phone,
        @NotBlank
        String address1,
        String address2,
        @NotBlank
        String city,
        String region,
        @NotBlank
        String postal,
        @NotBlank
        @Pattern(regexp = "^\\+?\\d{1,3}$")
        String countryCode

) {
}
