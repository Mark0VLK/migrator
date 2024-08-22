package org.example.migrator.repository;

import org.example.migrator.repository.entity.Country;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface CountryRepository extends PagingAndSortingRepository<Country, Long> {
    Optional<Country> findByName(String name);
}
