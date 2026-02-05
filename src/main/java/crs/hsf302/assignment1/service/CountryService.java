package crs.hsf302.assignment1.service;


import crs.hsf302.assignment1.domain.CountryRequest;
import crs.hsf302.assignment1.domain.dto.CountryDto;
import crs.hsf302.assignment1.domain.entity.Country;
import crs.hsf302.assignment1.exception.DuplicateCountryCodeException;
import crs.hsf302.assignment1.mapper.CountryMapper;
import crs.hsf302.assignment1.repository.CountryRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CountryService {

    private final CountryRepo countryRepo;
    private final CountryMapper countryMapper;

    public Country save(CountryRequest request){
        if(countryRepo.existsByCode(request.code())){
            throw new DuplicateCountryCodeException("Ma quoc gia da ton tai");
        }
        return countryRepo.save(new Country(request.name(), request.code()));
    }

    public List<Country> findAll(){
        return countryRepo.findAll();
    }

}
