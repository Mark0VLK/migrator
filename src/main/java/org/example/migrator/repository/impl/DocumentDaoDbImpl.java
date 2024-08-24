package org.example.migrator.repository.impl;

import lombok.RequiredArgsConstructor;
import org.example.migrator.repository.DocumentDao;
import org.example.migrator.repository.DocumentRepository;
import org.example.migrator.repository.entity.Document;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DocumentDaoDbImpl implements DocumentDao {

    private final DocumentRepository documentRepository;

    @Override
    public Document save(Document document) {
        return documentRepository.save(document);
    }
}
