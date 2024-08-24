package org.example.migrator.util;

import org.example.migrator.controller.response.DocumentResponse;
import org.example.migrator.repository.entity.Country;
import org.example.migrator.repository.entity.Document;
import org.example.migrator.repository.entity.Hotel;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public class EntityUtilBuilder {

    public static Document buildDocument(MultipartFile file, String fileName) throws IOException {
        return Document.builder()
                .fileName(fileName)
                .fileData(file.getBytes())
                .build();
    }

    public static DocumentResponse responseBuilder(String fileName, Long fileSize) {
        return DocumentResponse.builder()
                .fileName(fileName)
                .size(fileSize)
                .build();
    }

    public static Hotel buildHotel(String hotelName, String numberOfStars, String numberOfRooms, Country country) {
        return Hotel.builder()
                .name(hotelName)
                .numberOfStars(Integer.valueOf(numberOfStars))
                .numberOfRooms(Integer.valueOf(numberOfRooms))
                .country(country)
                .build();
    }
}
