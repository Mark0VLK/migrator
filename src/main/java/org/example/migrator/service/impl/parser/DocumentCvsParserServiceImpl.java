package org.example.migrator.service.impl.parser;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.migrator.repository.entity.Hotel;
import org.example.migrator.service.CountryService;
import org.example.migrator.service.HotelService;
import org.example.migrator.service.parser.DocumentParserService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import static java.lang.String.format;
import static org.example.migrator.enums.HotelCsvColumn.COUNTRY_AND_CITY;
import static org.example.migrator.enums.HotelCsvColumn.HOTEL_NAME;
import static org.example.migrator.enums.HotelCsvColumn.NUMBER_OF_ROOMS;
import static org.example.migrator.enums.HotelCsvColumn.NUMBER_OF_STARS;
import static org.example.migrator.util.EntityUtilBuilder.buildHotel;

@Slf4j
@Service
@RequiredArgsConstructor
public class DocumentCvsParserServiceImpl implements DocumentParserService {

    private final HotelService hotelService;
    private final CountryService countryService;
    private final static String SEPARATOR = " ";


    @Override
    public void fileParsing(MultipartFile file) throws Exception {
        int lineCounter = 0;
        try (InputStreamReader inputStreamReader = new InputStreamReader(file.getInputStream(),
                StandardCharsets.UTF_8);
             CSVReader csvReader = new CSVReaderBuilder(inputStreamReader).build()) {

            String[] nextLine;
            while ((nextLine = csvReader.readNext()) != null) {
                ++lineCounter;
                String hotelName = nextLine[HOTEL_NAME.column()];
                String numberOfStars = nextLine[NUMBER_OF_STARS.column()];
                String countryAndCity = nextLine[COUNTRY_AND_CITY.column()];
                String numberOfRooms = nextLine[NUMBER_OF_ROOMS.column()];

                String country = countryAndCity.split(SEPARATOR)[0];

                Hotel hotel = buildHotel(hotelName, numberOfStars, numberOfRooms, countryService.findByName(country));

                hotelService.create(hotel);
            }
        } catch (CsvValidationException e) {
            throw new CsvValidationException(
                    format("Ошибка валидации CSV: %s, произошла в строке номер %d", e.getMessage(), lineCounter));
        } catch (IOException e) {
            throw new IOException(
                    format("Ошибка чтения файла: %s, произошла в строке номер %d", e.getMessage(), lineCounter));
        } catch (Exception e) {
            throw new Exception(e);
        }
    }
}


