package com.fileconverter.generator;

import com.fileconverter.exception.FileConverterException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class JsonGeneratorTest {
    
    private JsonGenerator generator;
    
    @TempDir
    File tempDir;
    
    @BeforeEach
    void setUp() {
        generator = new JsonGenerator();
    }
    
    @Test
    void testGenerateSimpleObject() throws IOException, FileConverterException {
        Map<String, Object> data = new HashMap<>();
        data.put("name", "John");
        data.put("age", 30);
        
        File outputFile = new File(tempDir, "output.json");
        generator.generate(data, outputFile);
        
        assertTrue(outputFile.exists());
        
        // Verify content
        String content = Files.readString(outputFile.toPath());
        assertTrue(content.contains("\"name\" : \"John\""));
        assertTrue(content.contains("\"age\" : 30"));
    }
    
    @Test
    void testGenerateArray() throws IOException, FileConverterException {
        List<Map<String, Object>> data = List.of(
            Map.of("name", "John", "age", 30),
            Map.of("name", "Jane", "age", 25)
        );
        
        File outputFile = new File(tempDir, "array.json");
        generator.generate(data, outputFile);
        
        assertTrue(outputFile.exists());
        
        // Verify content
        String content = Files.readString(outputFile.toPath());
        assertTrue(content.contains("\"name\" : \"John\""));
        assertTrue(content.contains("\"name\" : \"Jane\""));
    }
    
    @Test
    void testGenerateNull() throws IOException, FileConverterException {
        File outputFile = new File(tempDir, "null.json");
        generator.generate(null, outputFile);
        
        assertTrue(outputFile.exists());
        
        // Verify content
        String content = Files.readString(outputFile.toPath());
        assertEquals("null", content.trim());
    }
    
    @Test
    void testGenerateNestedObject() throws IOException, FileConverterException {
        Map<String, Object> data = new HashMap<>();
        Map<String, Object> address = new HashMap<>();
        address.put("city", "New York");
        address.put("zip", "10001");
        
        data.put("name", "John");
        data.put("address", address);
        
        File outputFile = new File(tempDir, "nested.json");
        generator.generate(data, outputFile);
        
        assertTrue(outputFile.exists());
        
        // Verify content
        String content = Files.readString(outputFile.toPath());
        assertTrue(content.contains("\"name\" : \"John\""));
        assertTrue(content.contains("\"city\" : \"New York\""));
        assertTrue(content.contains("\"zip\" : \"10001\""));
    }
    
    @Test
    void testGenerateToNonExistentDirectory() throws FileConverterException {
        Map<String, Object> data = Map.of("name", "John");
        
        File nonExistentDir = new File(tempDir, "nonexistent");
        File outputFile = new File(nonExistentDir, "output.json");
        
        // This should fail because directory doesn't exist
        assertThrows(FileConverterException.class, () -> generator.generate(data, outputFile));
    }
}
