package org.example.migrator.service.migrator;

import org.example.migrator.controller.response.DocumentResponse;
import org.springframework.web.multipart.MultipartFile;

public interface MigratorService {

    DocumentResponse dataMigration(MultipartFile file);
}
