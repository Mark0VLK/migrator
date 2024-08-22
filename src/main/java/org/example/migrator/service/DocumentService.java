package org.example.migrator.service;

import org.example.migrator.controller.response.DocumentResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface DocumentService {
    DocumentResponse fileProcessing(MultipartFile file) throws IOException;
}
