package org.example.migrator.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.migrator.repository.HotelRepository;
import org.example.migrator.repository.entity.Hotel;
import org.example.migrator.service.HotelService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HotelServiceImpl implements HotelService {

    private final HotelRepository hotelRepository;

    @Override
    public Hotel create(Hotel hotel) {
        return hotelRepository.save(hotel);
    }
}
