package crs.hsf302.assignment1.controller;

import crs.hsf302.assignment1.domain.CountryRequest;
import crs.hsf302.assignment1.domain.dto.CountryDto;
import crs.hsf302.assignment1.exception.DuplicateCountryCodeException;
import crs.hsf302.assignment1.exception.DuplicateResourcceException;
import crs.hsf302.assignment1.mapper.CountryMapper;
import crs.hsf302.assignment1.service.CountryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping(path = "countries")
public class CountryController {
    private final CountryService countryService;
    private final CountryMapper mapper;

    @GetMapping(path = "/add")
    public String showCountryForm(Model model) {
        model.addAttribute("country",CountryDto.builder().build());
        return "country-form";
    }

    @PostMapping(path = "/save")
    public String addCountry(
            @Valid
            @ModelAttribute("country")
            CountryDto dto,
            BindingResult result,
            Model model

    ) {
        if (result.hasErrors()) {
            return "country-form";
        }
        try {
            countryService.save(mapper.fromDto(dto));
        } catch (DuplicateCountryCodeException e) {
            result.rejectValue("code", "error.country", e.getMessage());
            return "country-form";
        }
        return "redirect:/countries";
    }

    @GetMapping
    public String countryListView(Model model) {
        model.addAttribute("countries", countryService.findAll().stream().map(mapper::toDto).toList());
        return "country-list";
    }

}
