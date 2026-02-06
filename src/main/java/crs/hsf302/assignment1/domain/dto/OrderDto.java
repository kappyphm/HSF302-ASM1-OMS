package crs.hsf302.assignment1.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class OrderDto {
        private Integer id;
        private String code;
        private CustomerDto customer;
        private String address1;
        private String address2;
        private String city;
        private String region;
        private String postal;
        private String country;
}
