package org.example.migrator.service.parser;

import com.opencsv.exceptions.CsvValidationException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface DocumentParserService {

    void fileParsing(MultipartFile file) throws Exception;

}
