package crs.hsf302.assignment1.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;

@Builder
public record CountryDto(
        @NotBlank
        @Pattern(regexp = "^\\+?\\d{1,3}$")
        String code,
        @NotBlank
        String name
) {
}
