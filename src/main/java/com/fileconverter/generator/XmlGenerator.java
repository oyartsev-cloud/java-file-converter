package com.fileconverter.generator;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fileconverter.exception.FileConverterException;
import java.io.File;
import java.io.IOException;

/**
 * XML generator implementation using Jackson XML.
 */
public class XmlGenerator implements Generator {
    
    private final ObjectMapper xmlMapper;
    
    public XmlGenerator() {
        this.xmlMapper = new XmlMapper();
        // Configure XML output for better formatting
        this.xmlMapper.getFactory().getXMLOutputFactory().setProperty("javax.xml.stream.isRepairingNamespaces", false);
    }
    
    @Override
    public void generate(Object data, File file) throws FileConverterException {
        try {
            xmlMapper.writerWithDefaultPrettyPrinter().writeValue(file, data);
        } catch (IOException e) {
            throw new FileConverterException("Failed to generate XML file: " + e.getMessage(), e);
        }
    }
}
