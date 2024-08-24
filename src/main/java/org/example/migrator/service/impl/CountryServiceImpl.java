package org.example.migrator.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.migrator.repository.CountryRepository;
import org.example.migrator.repository.entity.Country;
import org.example.migrator.service.CountryService;
import org.springframework.stereotype.Service;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class CountryServiceImpl implements CountryService {

    private final CountryRepository countryRepository;

    @Override
    public Country findByName(String countryName) {
        return countryRepository.findByName(countryName)
                .orElseThrow(() -> new IllegalArgumentException(format("Страна с названием %s не найдена", countryName)));
    }
}
