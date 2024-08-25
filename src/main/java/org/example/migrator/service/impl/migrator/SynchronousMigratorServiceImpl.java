package org.example.migrator.service.impl.migrator;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.migrator.controller.response.DocumentResponse;
import org.example.migrator.exception.MigrationException;
import org.example.migrator.repository.entity.Document;
import org.example.migrator.service.DocumentService;
import org.example.migrator.service.migrator.MigratorService;
import org.example.migrator.service.parser.DocumentParserService;
import org.example.migrator.service.validator.FileValidationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import static org.example.migrator.util.EntityUtilBuilder.buildDocument;
import static org.example.migrator.util.EntityUtilBuilder.responseBuilder;

@Slf4j
@Service
@RequiredArgsConstructor
public class SynchronousMigratorServiceImpl implements MigratorService {

    private final DocumentService documentService;
    private final FileValidationService fileValidationService;
    private final DocumentParserService documentParserService;

    @Override
    @Transactional
    public DocumentResponse dataMigration(MultipartFile file) {
        log.info(" ----- синхронная обработка ----- ");
        final String fileName = file.getOriginalFilename();
        final Long fileSize = file.getSize() / 1024;
        try {
            if (fileValidationService.isFileExtensionAllowed(fileName) &&
                    fileValidationService.IsFileSizeAllowed(file.getSize())) {

                Document document = buildDocument(file, fileName);
                documentService.create(document);

                documentParserService.fileParsing(file);
            }
        } catch (Exception e) {
            throw new MigrationException(e.getMessage());
        }
        return responseBuilder(fileName, fileSize);
    }
}
