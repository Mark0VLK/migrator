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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

import static org.example.migrator.util.EntityUtilBuilder.buildDocument;
import static org.example.migrator.util.EntityUtilBuilder.responseBuilder;

@Slf4j
@Service
@RequiredArgsConstructor
public class MigratorServiceImpl implements MigratorService {

    private final DocumentService documentService;
    private final FileValidationService fileValidationService;
    private final DocumentParserService documentParserService;
    @Value("${file.processing.size.threshold}")
    private Long sizeThreshold;

    @Override
    @Transactional(rollbackFor = MigrationException.class)
    public DocumentResponse dataMigration(MultipartFile file) {
        final String fileName = file.getOriginalFilename();
        final Long fileSize = file.getSize() / 1024;
        try {
            if (fileValidationService.isFileExtensionAllowed(fileName) &&
                    fileValidationService.IsFileSizeAllowed(file.getSize())) {
                if (fileSize < sizeThreshold) {
                    synchronousDataMigration(file, fileName);
                } else {
                    asynchronousDataMigration(file, fileName);
                }
            }
        } catch (Exception e) {
            throw new MigrationException(e.getMessage());
        }
        return responseBuilder(fileName, fileSize);
    }

    private void synchronousDataMigration(MultipartFile file, String fileName) {
        log.info("\n----- cинхронная обработка -----\n");

        saveDocument(file, fileName);
        parseFile(file);

        log.info("\n----- все синхронные методы завершили работу -----\n");
    }

    private void asynchronousDataMigration(MultipartFile file, String fileName) {
        log.info("\n----- асинхронная обработка -----\n");

        CompletableFuture<Void> saveDocumentFuture = CompletableFuture.runAsync(() -> saveDocument(file, fileName));
        CompletableFuture<Void> parseFileFuture = CompletableFuture.runAsync(() -> parseFile(file));

        CompletableFuture<Void> combinedFuture = CompletableFuture.allOf(saveDocumentFuture, parseFileFuture);
        combinedFuture.join();

        log.info("\n----- все асинхронные методы завершили работу -----\n");
    }

    private void saveDocument(MultipartFile file, String fileName) {
        log.info("\n----- метод сохранения документа -----\n");
        Document document;
        try {
            document = buildDocument(file, fileName);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        documentService.create(document);
        log.info("\n----- метод сохранения документа завершил работу -----\n");
    }

    private void parseFile(MultipartFile file) {
        log.info("\n----- метод получения информации об отелях -----\n");
        try {
            documentParserService.fileParsing(file);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        log.info("\n----- метод получения информации об отелях завершил работу -----\n");
    }
}
