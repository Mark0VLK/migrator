package org.example.migrator.controller.response;

import lombok.Builder;

@Builder
public record ErrorMessage(
        Integer errorCode,
        String errorMessage
) {
}
