package org.example.migrator.service;

import org.example.migrator.repository.entity.Country;

public interface CountryService {

    Country findByName(String countryName);
}
