package com.fileconverter.exception;

/**
 * Exception thrown when file parsing fails.
 */
public class ParsingException extends FileConverterException {
    
    public ParsingException(String message) {
        super("Parsing error: " + message);
    }
    
    public ParsingException(String message, Throwable cause) {
        super("Parsing error: " + message, cause);
    }
}
