package org.example.migrator.exception;

public class FileAllowedException extends RuntimeException {
    public FileAllowedException(String msg) {
        super(msg);
    }
}
