package com.fileconverter.generator;

import com.fileconverter.exception.UnsupportedFormatException;
import com.fileconverter.utils.FileFormat;

/**
 * Factory for creating generators based on file format.
 * Implements Factory Method pattern.
 */
public class GeneratorFactory {
    
    /**
     * Create a generator for the specified file format.
     * 
     * @param format the file format
     * @return appropriate generator instance
     * @throws UnsupportedFormatException if format is not supported
     */
    public static Generator createGenerator(FileFormat format) throws UnsupportedFormatException {
        if (format == null) {
            throw new UnsupportedFormatException("null");
        }
        
        switch (format) {
            case JSON:
                return new JsonGenerator();
            case XML:
                return new XmlGenerator();
            case CSV:
                return new CsvGenerator();
            default:
                throw new UnsupportedFormatException(format.name(), "JSON, XML, CSV");
        }
    }
    
    /**
     * Create a generator by detecting format from file path.
     * 
     * @param filePath the file path
     * @return appropriate generator instance
     * @throws UnsupportedFormatException if format cannot be detected or is not supported
     */
    public static Generator createGenerator(String filePath) throws UnsupportedFormatException {
        FileFormat format = FileFormat.fromFilePath(filePath);
        if (format == null) {
            throw new UnsupportedFormatException("Could not detect format from file path: " + filePath);
        }
        return createGenerator(format);
    }
}
