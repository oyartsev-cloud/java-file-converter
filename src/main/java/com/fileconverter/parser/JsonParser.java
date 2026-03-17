package com.fileconverter.parser;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fileconverter.exception.ParsingException;
import java.io.File;
import java.io.IOException;

/**
 * JSON parser implementation using Jackson.
 */
public class JsonParser implements Parser {
    
    private final ObjectMapper objectMapper;
    
    public JsonParser() {
        this.objectMapper = new ObjectMapper();
    }
    
    @Override
    public Object parse(File file) throws ParsingException {
        try {
            return objectMapper.readValue(file, Object.class);
        } catch (IOException e) {
            throw new ParsingException("Failed to parse JSON file: " + e.getMessage(), e);
        }
    }
}
