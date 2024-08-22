package org.example.migrator.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@ConfigurationProperties(prefix = "file.properties")
public record FileProperties(
        List<String> allowedExtensions,
        Long maxSize
) {
}
