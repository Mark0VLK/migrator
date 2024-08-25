package org.example.migrator.controller;

import lombok.RequiredArgsConstructor;
import org.example.migrator.controller.response.DocumentResponse;
import org.example.migrator.service.impl.migrator.AsynchronousMigratorServiceImpl;
import org.example.migrator.service.impl.migrator.SynchronousMigratorServiceImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("api/v1/parser")
@RequiredArgsConstructor
public class MigratorController {

    private final SynchronousMigratorServiceImpl synchronousMigratorService;
    private final AsynchronousMigratorServiceImpl asynchronousMigratorService;

    @Value("${file.processing.size.threshold}")
    private Long sizeThreshold;

    @PostMapping
    public ResponseEntity<DocumentResponse> sendDocuments(@RequestBody MultipartFile file) {
        long fileSize = file.getSize() / 1024;
        DocumentResponse response = fileSize < sizeThreshold ?
                synchronousMigratorService.dataMigration(file) : asynchronousMigratorService.dataMigration(file);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
