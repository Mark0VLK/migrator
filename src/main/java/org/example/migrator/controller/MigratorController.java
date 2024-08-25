package org.example.migrator.controller;

import lombok.RequiredArgsConstructor;
import org.example.migrator.controller.response.DocumentResponse;
import org.example.migrator.service.migrator.MigratorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("api/v1/parser")
@RequiredArgsConstructor
public class MigratorController {

    private final MigratorService migratorService;

    @PostMapping
    public ResponseEntity<DocumentResponse> sendDocuments(@RequestBody MultipartFile file) throws IOException {
        return new ResponseEntity<>(migratorService.dataMigration(file), HttpStatus.OK);
    }
}
