package com.fileconverter.exception;

/**
 * Exception thrown when an unsupported file format is encountered.
 */
public class UnsupportedFormatException extends FileConverterException {
    
    public UnsupportedFormatException(String format) {
        super("Unsupported file format: " + format);
    }
    
    public UnsupportedFormatException(String format, String supportedFormats) {
        super("Unsupported file format: " + format + ". Supported formats: " + supportedFormats);
    }
}
