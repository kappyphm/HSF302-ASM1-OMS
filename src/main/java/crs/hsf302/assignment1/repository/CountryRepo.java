package crs.hsf302.assignment1.repository;

import crs.hsf302.assignment1.domain.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CountryRepo extends JpaRepository<Country, String> {
    Boolean existsByCode(String code);
}

