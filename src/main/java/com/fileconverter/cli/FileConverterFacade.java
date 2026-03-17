package com.fileconverter.cli;

import com.fileconverter.conversion.FileConversionService;
import com.fileconverter.exception.FileConverterException;
import com.fileconverter.utils.FileFormat;

import java.io.File;

/**
 * Facade for file conversion operations.
 * Implements Facade pattern to provide a simplified interface.
 */
public class FileConverterFacade {
    
    public FileConverterFacade() {
        // Facade pattern - simplified interface
    }
    
    /**
     * Convert file with auto-detected formats.
     * 
     * @param inputFile the input file
     * @param outputFile the output file
     * @throws FileConverterException if conversion fails
     */
    public void convert(File inputFile, File outputFile) throws FileConverterException {
        FileConversionService.convertAuto(inputFile, outputFile);
    }
    
    /**
     * Convert file with specified formats.
     * 
     * @param inputFile the input file
     * @param outputFile the output file
     * @param inputFormatStr input format string (null to auto-detect)
     * @param outputFormatStr output format string (null to auto-detect)
     * @throws FileConverterException if conversion fails
     */
    public void convert(File inputFile, File outputFile, String inputFormatStr, String outputFormatStr) 
            throws FileConverterException {
        
        FileFormat inputFormat = parseFormat(inputFormatStr, "input");
        FileFormat outputFormat = parseFormat(outputFormatStr, "output");
        
        // Auto-detect if not specified
        if (inputFormat == null) {
            inputFormat = FileFormat.fromFilePath(inputFile.getPath());
            if (inputFormat == null) {
                throw new FileConverterException("Could not detect input file format from: " + inputFile.getPath());
            }
        }
        
        if (outputFormat == null) {
            outputFormat = FileFormat.fromFilePath(outputFile.getPath());
            if (outputFormat == null) {
                throw new FileConverterException("Could not detect output file format from: " + outputFile.getPath());
            }
        }
        
        FileConversionService service = new FileConversionService(inputFormat, outputFormat);
        service.convert(inputFile, outputFile);
    }
    
    private FileFormat parseFormat(String formatStr, String type) throws FileConverterException {
        if (formatStr == null || formatStr.trim().isEmpty()) {
            return null;
        }
        
        try {
            return FileFormat.valueOf(formatStr.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new FileConverterException(
                String.format("Invalid %s format: %s. Supported formats: JSON, XML, CSV", type, formatStr)
            );
        }
    }
}
