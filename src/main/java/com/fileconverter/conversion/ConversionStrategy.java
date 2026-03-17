package com.fileconverter.conversion;

import com.fileconverter.exception.FileConverterException;
import java.io.File;

/**
 * Interface for conversion strategies.
 * Implements Strategy pattern.
 */
public interface ConversionStrategy {
    
    /**
     * Convert data from input format to output format.
     * 
     * @param inputFile the input file
     * @param outputFile the output file
     * @throws FileConverterException if conversion fails
     */
    void convert(File inputFile, File outputFile) throws FileConverterException;
}
