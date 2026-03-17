package com.fileconverter.parser;

import com.fileconverter.exception.ParsingException;
import java.io.File;
import java.util.Map;

/**
 * Interface for file parsers.
 */
public interface Parser {
    
    /**
     * Parse a file and return data as a generic structure.
     * 
     * @param file the file to parse
     * @return parsed data as Map for objects or List for arrays
     * @throws ParsingException if parsing fails
     */
    Object parse(File file) throws ParsingException;
}
