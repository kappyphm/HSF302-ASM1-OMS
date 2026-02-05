package crs.hsf302.assignment1.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;


@Builder
public record CreateOrderRequest(
        String firstName,
        String lastName,
        String email,
        String phone,
        String address1,
        String address2,
        String city,
        String region,
        String postal,
        String countryCode
) {}