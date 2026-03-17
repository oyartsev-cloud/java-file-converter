package com.fileconverter.conversion;

import com.fileconverter.exception.FileConverterException;
import com.fileconverter.exception.ParsingException;
import com.fileconverter.exception.UnsupportedFormatException;
import com.fileconverter.generator.Generator;
import com.fileconverter.generator.GeneratorFactory;
import com.fileconverter.parser.Parser;
import com.fileconverter.parser.ParserFactory;
import com.fileconverter.utils.FileFormat;
import java.io.File;

/**
 * Service for file conversion operations.
 * Implements Strategy pattern for different conversion scenarios.
 */
public class FileConversionService implements ConversionStrategy {
    
    private final FileFormat inputFormat;
    private final FileFormat outputFormat;
    
    /**
     * Create a conversion service for specific input and output formats.
     * 
     * @param inputFormat the input file format
     * @param outputFormat the output file format
     */
    public FileConversionService(FileFormat inputFormat, FileFormat outputFormat) {
        this.inputFormat = inputFormat;
        this.outputFormat = outputFormat;
    }
    
    @Override
    public void convert(File inputFile, File outputFile) throws FileConverterException {
        validateInputFile(inputFile);
        
        try {
            // Create appropriate parser and generator
            Parser parser = ParserFactory.createParser(inputFormat);
            Generator generator = GeneratorFactory.createGenerator(outputFormat);
            
            // Parse input file
            Object data = parser.parse(inputFile);
            
            // Generate output file
            generator.generate(data, outputFile);
            
        } catch (UnsupportedFormatException e) {
            throw new FileConverterException("Unsupported format: " + e.getMessage(), e);
        } catch (ParsingException e) {
            throw new FileConverterException("Failed to parse input file: " + e.getMessage(), e);
        }
    }
    
    /**
     * Convert file by auto-detecting formats from file extensions.
     * 
     * @param inputFile the input file
     * @param outputFile the output file
     * @throws FileConverterException if conversion fails
     */
    public static void convertAuto(File inputFile, File outputFile) throws FileConverterException {
        validateInputFile(inputFile);
        
        FileFormat inputFormat = FileFormat.fromFilePath(inputFile.getPath());
        FileFormat outputFormat = FileFormat.fromFilePath(outputFile.getPath());
        
        if (inputFormat == null) {
            throw new FileConverterException("Could not detect input file format from: " + inputFile.getPath());
        }
        
        if (outputFormat == null) {
            throw new FileConverterException("Could not detect output file format from: " + outputFile.getPath());
        }
        
        FileConversionService service = new FileConversionService(inputFormat, outputFormat);
        service.convert(inputFile, outputFile);
    }
    
    private static void validateInputFile(File inputFile) throws FileConverterException {
        if (inputFile == null) {
            throw new FileConverterException("Input file cannot be null");
        }
        
        if (!inputFile.exists()) {
            throw new FileConverterException("Input file does not exist: " + inputFile.getPath());
        }
        
        if (!inputFile.isFile()) {
            throw new FileConverterException("Input path is not a file: " + inputFile.getPath());
        }
        
        if (!inputFile.canRead()) {
            throw new FileConverterException("Cannot read input file: " + inputFile.getPath());
        }
    }
}
