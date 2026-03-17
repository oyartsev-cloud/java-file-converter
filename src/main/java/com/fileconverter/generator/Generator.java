package com.fileconverter.generator;

import com.fileconverter.exception.FileConverterException;
import java.io.File;

/**
 * Interface for file generators.
 */
public interface Generator {
    
    /**
     * Generate a file from the given data.
     * 
     * @param data the data to write
     * @param file the output file
     * @throws FileConverterException if generation fails
     */
    void generate(Object data, File file) throws FileConverterException;
}
