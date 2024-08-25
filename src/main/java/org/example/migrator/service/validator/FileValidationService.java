package org.example.migrator.service.validator;

public interface FileValidationService {

    boolean IsFileSizeAllowed(Long size);

    boolean isFileExtensionAllowed(String originalFilename);
}
