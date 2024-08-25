package org.example.migrator.service;

import org.example.migrator.repository.entity.City;

public interface CityService {

    City findByName(String cityName);
}
