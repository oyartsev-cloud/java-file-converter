package com.fileconverter.cli;

import com.fileconverter.exception.FileConverterException;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

import java.io.File;
import java.util.concurrent.Callable;

/**
 * Main CLI application for file conversion.
 */
@Command(
    name = "file-converter",
    mixinStandardHelpOptions = true,
    version = "File Converter 1.0.0",
    description = "Convert files between JSON, XML, and CSV formats"
)
public class FileConverterApplication implements Callable<Integer> {
    
    @Parameters(
        index = "0",
        description = "Input file path"
    )
    private File inputFile;
    
    @Parameters(
        index = "1",
        description = "Output file path"
    )
    private File outputFile;
    
    @Option(
        names = {"-i", "--input-format"},
        description = "Input file format (json, xml, csv). Auto-detected if not specified."
    )
    private String inputFormat;
    
    @Option(
        names = {"-o", "--output-format"},
        description = "Output file format (json, xml, csv). Auto-detected if not specified."
    )
    private String outputFormat;
    
    @Option(
        names = {"-v", "--verbose"},
        description = "Enable verbose output"
    )
    private boolean verbose;
    
    public static void main(String[] args) {
        int exitCode = new CommandLine(new FileConverterApplication()).execute(args);
        System.exit(exitCode);
    }
    
    @Override
    public Integer call() throws Exception {
        try {
            // Validate input file
            if (inputFile == null) {
                System.err.println("Error: Input file is required");
                return 1;
            }
            
            if (!inputFile.exists()) {
                System.err.println("Error: Input file does not exist: " + inputFile.getPath());
                return 1;
            }
            
            if (!inputFile.canRead()) {
                System.err.println("Error: Cannot read input file: " + inputFile.getPath());
                return 1;
            }
            
            // Create output directory if it doesn't exist
            File outputDir = outputFile.getParentFile();
            if (outputDir != null && !outputDir.exists()) {
                if (!outputDir.mkdirs()) {
                    System.err.println("Error: Cannot create output directory: " + outputDir.getPath());
                    return 1;
                }
            }
            
            // Perform conversion
            FileConverterFacade converter = new FileConverterFacade();
            
            if (verbose) {
                System.out.println("Converting " + inputFile.getPath() + " to " + outputFile.getPath());
                if (inputFormat != null) {
                    System.out.println("Input format: " + inputFormat);
                }
                if (outputFormat != null) {
                    System.out.println("Output format: " + outputFormat);
                }
            }
            
            converter.convert(inputFile, outputFile, inputFormat, outputFormat);
            
            if (verbose) {
                System.out.println("Conversion completed successfully!");
            } else {
                System.out.println("File converted successfully: " + outputFile.getPath());
            }
            
            return 0;
            
        } catch (FileConverterException e) {
            System.err.println("Error: " + e.getMessage());
            if (verbose) {
                e.printStackTrace();
            }
            return 1;
        } catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
            if (verbose) {
                e.printStackTrace();
            }
            return 1;
        }
    }
}
