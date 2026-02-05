package crs.hsf302.assignment1.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;

import java.util.Set;


public record CreateProgramDto(
        @NotBlank
        String name,
        String descrition
) {
}
