package com.fileconverter.conversion;

import com.fileconverter.exception.FileConverterException;
import com.fileconverter.utils.FileFormat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.*;

class FileConversionServiceTest {
    
    @TempDir
    File tempDir;
    
    private File jsonFile;
    private File xmlFile;
    private File csvFile;
    
    @BeforeEach
    void setUp() throws IOException {
        // Create test JSON file
        jsonFile = new File(tempDir, "test.json");
        try (FileWriter writer = new FileWriter(jsonFile)) {
            writer.write("{\"name\": \"John\", \"age\": 30}");
        }
        
        // Create test CSV file
        csvFile = new File(tempDir, "test.csv");
        try (FileWriter writer = new FileWriter(csvFile)) {
            writer.write("name,age\n");
            writer.write("John,30\n");
            writer.write("Jane,25\n");
        }
        
        // Create test XML file
        xmlFile = new File(tempDir, "test.xml");
        try (FileWriter writer = new FileWriter(xmlFile)) {
            writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
            writer.write("<root>\n");
            writer.write("  <name>John</name>\n");
            writer.write("  <age>30</age>\n");
            writer.write("</root>");
        }
    }
    
    @Test
    void testJsonToXmlConversion() throws FileConverterException, IOException {
        File outputFile = new File(tempDir, "output.xml");
        
        FileConversionService service = new FileConversionService(FileFormat.JSON, FileFormat.XML);
        service.convert(jsonFile, outputFile);
        
        assertTrue(outputFile.exists());
        String content = readFileContent(outputFile);
        assertTrue(content.contains("<name>John</name>"));
        assertTrue(content.contains("<age>30</age>"));
    }
    
    @Test
    void testJsonToCsvConversion() throws FileConverterException, IOException {
        File outputFile = new File(tempDir, "output.csv");
        
        FileConversionService service = new FileConversionService(FileFormat.JSON, FileFormat.CSV);
        service.convert(jsonFile, outputFile);
        
        assertTrue(outputFile.exists());
        String content = readFileContent(outputFile);
        assertTrue(content.contains("name"));
        assertTrue(content.contains("age"));
        assertTrue(content.contains("John"));
        assertTrue(content.contains("30"));
    }
    
    @Test
    void testCsvToJsonConversion() throws FileConverterException, IOException {
        File outputFile = new File(tempDir, "output.json");
        
        FileConversionService service = new FileConversionService(FileFormat.CSV, FileFormat.JSON);
        service.convert(csvFile, outputFile);
        
        assertTrue(outputFile.exists());
        String content = readFileContent(outputFile);
        assertTrue(content.contains("John"));
        assertTrue(content.contains("Jane"));
        assertTrue(content.contains("30"));
        assertTrue(content.contains("25"));
    }
    
    @Test
    void testCsvToXmlConversion() throws FileConverterException, IOException {
        File outputFile = new File(tempDir, "output.xml");
        
        FileConversionService service = new FileConversionService(FileFormat.CSV, FileFormat.XML);
        service.convert(csvFile, outputFile);
        
        assertTrue(outputFile.exists());
        String content = readFileContent(outputFile);
        assertTrue(content.contains("John"));
        assertTrue(content.contains("Jane"));
    }
    
    @Test
    void testXmlToJsonConversion() throws FileConverterException, IOException {
        File outputFile = new File(tempDir, "output.json");
        
        FileConversionService service = new FileConversionService(FileFormat.XML, FileFormat.JSON);
        service.convert(xmlFile, outputFile);
        
        assertTrue(outputFile.exists());
        String content = readFileContent(outputFile);
        assertTrue(content.contains("John"));
        assertTrue(content.contains("30"));
    }
    
    @Test
    void testXmlToCsvConversion() throws FileConverterException, IOException {
        File outputFile = new File(tempDir, "output.csv");
        
        FileConversionService service = new FileConversionService(FileFormat.XML, FileFormat.CSV);
        service.convert(xmlFile, outputFile);
        
        assertTrue(outputFile.exists());
        String content = readFileContent(outputFile);
        assertTrue(content.contains("John"));
        assertTrue(content.contains("30"));
    }
    
    @Test
    void testConvertAuto() throws FileConverterException, IOException {
        File outputFile = new File(tempDir, "auto_output.xml");
        
        FileConversionService.convertAuto(jsonFile, outputFile);
        
        assertTrue(outputFile.exists());
        String content = readFileContent(outputFile);
        assertTrue(content.contains("<name>John</name>"));
    }
    
    @Test
    void testConvertNonExistentInputFile() {
        File nonExistentFile = new File(tempDir, "nonexistent.json");
        File outputFile = new File(tempDir, "output.xml");
        
        FileConversionService service = new FileConversionService(FileFormat.JSON, FileFormat.XML);
        
        assertThrows(FileConverterException.class, () -> service.convert(nonExistentFile, outputFile));
    }
    
    @Test
    void testConvertToNonExistentDirectory() {
        File outputFile = new File(new File(tempDir, "nonexistent"), "output.xml");
        
        FileConversionService service = new FileConversionService(FileFormat.JSON, FileFormat.XML);
        
        // This should work because the directory will be created automatically
        assertDoesNotThrow(() -> service.convert(jsonFile, outputFile));
        assertTrue(outputFile.exists());
    }
    
    private String readFileContent(File file) throws IOException {
        return Files.readString(file.toPath());
    }
}
