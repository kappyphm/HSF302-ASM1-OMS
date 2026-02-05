package crs.hsf302.assignment1.mapper;

import crs.hsf302.assignment1.domain.CountryRequest;
import crs.hsf302.assignment1.domain.dto.CountryDto;
import crs.hsf302.assignment1.domain.entity.Country;
import org.springframework.stereotype.Component;

@Component
public class CountryMapper {
    public CountryRequest fromDto(CountryDto dto){
        return new CountryRequest(dto.name(), dto.code());
    }

    public CountryDto toDto(Country country){
        return new CountryDto(country.getName(),  country.getCode());
    }
}
