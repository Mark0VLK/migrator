package org.example.migrator.repository;

import org.example.migrator.repository.entity.Document;

public interface DocumentDao {

    Document save(Document document);
}
