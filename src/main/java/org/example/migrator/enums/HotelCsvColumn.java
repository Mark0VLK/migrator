package org.example.migrator.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum HotelCsvColumn {
    HOTEL_NAME(0),
    NUMBER_OF_STARS(1),
    COUNTRY_AND_CITY(2),
    NUMBER_OF_ROOMS(3);

    private final int columnNumber;

    public int column() {
        return this.columnNumber;
    }
}
