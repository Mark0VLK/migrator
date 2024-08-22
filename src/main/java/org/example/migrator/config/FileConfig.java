package org.example.migrator.config;

import org.example.migrator.config.properties.FileProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(FileProperties.class)
public class FileConfig {
}
