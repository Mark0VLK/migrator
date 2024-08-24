package org.example.migrator.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.migrator.repository.CityRepository;
import org.example.migrator.repository.entity.City;
import org.example.migrator.service.CityService;
import org.springframework.stereotype.Service;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class CityServiceImpl implements CityService {

    private final CityRepository cityRepository;

    @Override
    public City findByName(String cityName) {
        return cityRepository.findByName(cityName)
                .orElseThrow(() -> new IllegalArgumentException(format("Город с названием %s не существует", cityName)));
    }
}
