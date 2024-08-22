package org.example.migrator.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ApplicationErrorCodes {

    FATAL_ERROR(1),
    FILE_ALLOWED_ERROR(3),
    IO_ERROR(5);

    private final int codeId;
}
