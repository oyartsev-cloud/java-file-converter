package com.fileconverter.exception;

/**
 * Base exception for file converter operations.
 */
public class FileConverterException extends Exception {
    
    public FileConverterException(String message) {
        super(message);
    }
    
    public FileConverterException(String message, Throwable cause) {
        super(message, cause);
    }
}
