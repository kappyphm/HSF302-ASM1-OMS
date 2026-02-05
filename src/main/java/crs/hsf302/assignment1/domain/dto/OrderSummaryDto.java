package crs.hsf302.assignment1.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class OrderSummaryDto {
    private Integer id;
    private String  code;
    private String fullname;
    private String email;
    private String address;
    private String city;
}
