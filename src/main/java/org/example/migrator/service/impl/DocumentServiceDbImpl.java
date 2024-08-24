package org.example.migrator.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.migrator.repository.DocumentDao;
import org.example.migrator.repository.entity.Document;
import org.example.migrator.service.DocumentService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DocumentServiceDbImpl implements DocumentService {

    private final DocumentDao documentDao;

    @Override
    public Document create(Document document) {
        return documentDao.save(document);
    }
}
