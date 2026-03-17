package com.fileconverter.parser;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fileconverter.exception.ParsingException;
import java.io.File;
import java.io.IOException;

/**
 * XML parser implementation using Jackson XML.
 */
public class XmlParser implements Parser {
    
    private final ObjectMapper xmlMapper;
    
    public XmlParser() {
        this.xmlMapper = new XmlMapper();
    }
    
    @Override
    public Object parse(File file) throws ParsingException {
        try {
            return xmlMapper.readValue(file, Object.class);
        } catch (IOException e) {
            throw new ParsingException("Failed to parse XML file: " + e.getMessage(), e);
        }
    }
}
