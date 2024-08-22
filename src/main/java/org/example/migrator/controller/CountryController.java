package org.example.migrator.controller;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.example.migrator.repository.CountryRepository;
import org.example.migrator.repository.entity.Country;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/country")
@RequiredArgsConstructor
public class CountryController {

    private final CountryRepository countryRepository;

    @GetMapping
    public Page<Country> getAll(
            @RequestParam(value = "offset", defaultValue = "0") @Min(0) Integer offset,
            @RequestParam(value = "limit", defaultValue = "5") @Min(1) @Max(100) Integer limit) {

        return countryRepository.findAll(PageRequest.of(offset, limit));
    }
}
