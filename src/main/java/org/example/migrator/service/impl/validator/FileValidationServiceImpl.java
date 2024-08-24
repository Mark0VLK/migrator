package org.example.migrator.service.impl.validator;

import lombok.RequiredArgsConstructor;
import org.example.migrator.config.properties.FileProperties;
import org.example.migrator.exception.FileAllowedException;
import org.example.migrator.service.validator.FileValidationService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FileValidationServiceImpl implements FileValidationService {

    private final FileProperties fileProperties;

    @Override
    public boolean IsFileSizeAllowed(Long size) {
        long fileSizeInKilobytes = size / 1024L;
        if (fileSizeInKilobytes < fileProperties.maxSize())
            return true;
        throw new FileAllowedException("Превышен допустимый размер файла!");
    }

    @Override
    public boolean isFileExtensionAllowed(String originalFilename) {
        String extension = getFileExtension(originalFilename);
        if (fileProperties.allowedExtensions().contains(extension))
            return true;
        throw new FileAllowedException("Недопустимое расширение!");
    }

    private String getFileExtension(String originalFilename) {
        if (originalFilename != null && originalFilename.contains(".")) {
            return originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
        }
        return null;
    }
}
