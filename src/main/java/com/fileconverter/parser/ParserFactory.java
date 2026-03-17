package com.fileconverter.parser;

import com.fileconverter.exception.UnsupportedFormatException;
import com.fileconverter.utils.FileFormat;

/**
 * Factory for creating parsers based on file format.
 * Implements Factory Method pattern.
 */
public class ParserFactory {
    
    /**
     * Create a parser for the specified file format.
     * 
     * @param format the file format
     * @return appropriate parser instance
     * @throws UnsupportedFormatException if format is not supported
     */
    public static Parser createParser(FileFormat format) throws UnsupportedFormatException {
        if (format == null) {
            throw new UnsupportedFormatException("null");
        }
        
        switch (format) {
            case JSON:
                return new JsonParser();
            case XML:
                return new XmlParser();
            case CSV:
                return new CsvParser();
            default:
                throw new UnsupportedFormatException(format.name(), "JSON, XML, CSV");
        }
    }
    
    /**
     * Create a parser by detecting format from file path.
     * 
     * @param filePath the file path
     * @return appropriate parser instance
     * @throws UnsupportedFormatException if format cannot be detected or is not supported
     */
    public static Parser createParser(String filePath) throws UnsupportedFormatException {
        FileFormat format = FileFormat.fromFilePath(filePath);
        if (format == null) {
            throw new UnsupportedFormatException("Could not detect format from file path: " + filePath);
        }
        return createParser(format);
    }
}
