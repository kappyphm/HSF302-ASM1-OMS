package crs.hsf302.assignment1.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class ProgramDto {
    private UUID id;
    private String description;
    private String name;
    private List<OrderSummaryDto> orders;
}
