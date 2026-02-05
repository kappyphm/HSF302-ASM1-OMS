package crs.hsf302.assignment1.domain;

import lombok.Builder;


public record CountryRequest(
        String name,
        String code
) {
}
