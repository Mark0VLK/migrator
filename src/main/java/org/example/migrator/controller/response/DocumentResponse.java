package org.example.migrator.controller.response;

import lombok.Builder;

@Builder
public record DocumentResponse(
        String fileName,
        Long size
) {
}
