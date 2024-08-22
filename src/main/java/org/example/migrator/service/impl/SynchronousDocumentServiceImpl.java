package org.example.migrator.service.impl;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.migrator.config.properties.FileProperties;
import org.example.migrator.controller.response.DocumentResponse;
import org.example.migrator.exception.FileAllowedException;
import org.example.migrator.repository.CityRepository;
import org.example.migrator.repository.CountryRepository;
import org.example.migrator.repository.DocumentRepository;
import org.example.migrator.repository.HotelRepository;
import org.example.migrator.repository.entity.City;
import org.example.migrator.repository.entity.Country;
import org.example.migrator.repository.entity.Document;
import org.example.migrator.repository.entity.Hotel;
import org.example.migrator.service.DocumentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import static java.lang.String.format;
import static org.example.migrator.enums.HotelCsvColumn.COUNTRY_AND_CITY;
import static org.example.migrator.enums.HotelCsvColumn.HOTEL_NAME;
import static org.example.migrator.enums.HotelCsvColumn.NUMBER_OF_ROOMS;
import static org.example.migrator.enums.HotelCsvColumn.NUMBER_OF_STARS;

@Slf4j
@Service
@RequiredArgsConstructor
public class SynchronousDocumentServiceImpl implements DocumentService {

    private final HotelRepository hotelRepository;
    private final CountryRepository countryRepository;
    private final CityRepository cityRepository;
    private final DocumentRepository documentRepository;
    private final FileProperties fileProperties;

    private String getFileExtension(String originalFilename) {
        if (originalFilename != null && originalFilename.contains(".")) {
            return originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
        }
        return null;
    }

    private boolean IsFileSizeAllowed(Long size) {
        long fileSizeInKilobytes = size / 1024L;
        if (fileSizeInKilobytes < fileProperties.maxSize())
            return true;
        throw new FileAllowedException("Превышен допустимый размер файла!");
    }

    private boolean isFileExtensionAllowed(String originalFilename) {
        String extension = getFileExtension(originalFilename);
        if (fileProperties.allowedExtensions().contains(extension))
            return true;
        else throw new FileAllowedException("Недопустимое расширение!");
    }

    private Country getCountryByName(String countryName) {
        return countryRepository.findByName(countryName)
                .orElseThrow(() -> new IllegalArgumentException(format("Страна с названием %s не найдена", countryName)));
    }

    private City getCityByName(String cityName) {
        return cityRepository.findByName(cityName)
                .orElseThrow(() -> new IllegalArgumentException(format("Город с названием %s не существует", cityName)));
    }

    @Override
    @Transactional
    public DocumentResponse fileProcessing(MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename();
        Long fileSize = file.getSize();
        if (isFileExtensionAllowed(fileName) && IsFileSizeAllowed(file.getSize())) {

            Document document = Document.builder()
                    .fileName(fileName)
                    .fileData(file.getBytes())
                    .build();
            documentRepository.save(document);

            try (InputStreamReader inputStreamReader = new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8);
                 CSVReader csvReader = new CSVReaderBuilder(inputStreamReader).build()) {

                String[] nextLine;
                while ((nextLine = csvReader.readNext()) != null) {
                    String hotelName = nextLine[HOTEL_NAME.column()];
                    String numberOfStars = nextLine[NUMBER_OF_STARS.column()];
                    String countryAndCity = nextLine[COUNTRY_AND_CITY.column()];
                    String numberOfRooms = nextLine[NUMBER_OF_ROOMS.column()];

                    String[] location = countryAndCity.split(" ");
                    String country = location[0];
                    String city = location[1];

                    Hotel hotel = Hotel.builder()
                            .name(hotelName)
                            .numberOfStars(Integer.valueOf(numberOfStars))
                            .numberOfRooms(Integer.valueOf(numberOfRooms))
                            .country(getCountryByName(country))
                            .city(getCityByName(city))
                            .build();

                    hotelRepository.save(hotel);
                }
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        }
        return DocumentResponse.builder()
                .fileName(fileName)
                .size(fileSize)
                .build();
    }
}
