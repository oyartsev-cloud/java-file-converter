package com.fileconverter.generator;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fileconverter.exception.FileConverterException;
import java.io.File;
import java.io.IOException;

/**
 * JSON generator implementation using Jackson.
 */
public class JsonGenerator implements Generator {
    
    private final ObjectMapper objectMapper;
    
    public JsonGenerator() {
        this.objectMapper = new ObjectMapper();
    }
    
    @Override
    public void generate(Object data, File file) throws FileConverterException {
        try {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(file, data);
        } catch (IOException e) {
            throw new FileConverterException("Failed to generate JSON file: " + e.getMessage(), e);
        }
    }
}
