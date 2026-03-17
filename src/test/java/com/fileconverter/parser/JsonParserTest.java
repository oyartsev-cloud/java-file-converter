package com.fileconverter.parser;

import com.fileconverter.exception.ParsingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class JsonParserTest {
    
    private JsonParser parser;
    
    @TempDir
    File tempDir;
    
    @BeforeEach
    void setUp() {
        parser = new JsonParser();
    }
    
    @Test
    void testParseSimpleObject() throws IOException, ParsingException {
        // Create test JSON file
        File jsonFile = new File(tempDir, "test.json");
        try (FileWriter writer = new FileWriter(jsonFile)) {
            writer.write("{\"name\": \"John\", \"age\": 30}");
        }
        
        Object result = parser.parse(jsonFile);
        
        assertNotNull(result);
        assertInstanceOf(Map.class, result);
        
        @SuppressWarnings("unchecked")
        Map<String, Object> map = (Map<String, Object>) result;
        assertEquals("John", map.get("name"));
        assertEquals(30, map.get("age"));
    }
    
    @Test
    void testParseArray() throws IOException, ParsingException {
        // Create test JSON file with array
        File jsonFile = new File(tempDir, "array.json");
        try (FileWriter writer = new FileWriter(jsonFile)) {
            writer.write("[{\"name\": \"John\"}, {\"name\": \"Jane\"}]");
        }
        
        Object result = parser.parse(jsonFile);
        
        assertNotNull(result);
        assertInstanceOf(List.class, result);
        
        @SuppressWarnings("unchecked")
        List<Map<String, Object>> list = (List<Map<String, Object>>) result;
        assertEquals(2, list.size());
        assertEquals("John", list.get(0).get("name"));
        assertEquals("Jane", list.get(1).get("name"));
    }
    
    @Test
    void testParseNestedObject() throws IOException, ParsingException {
        // Create test JSON file with nested object
        File jsonFile = new File(tempDir, "nested.json");
        try (FileWriter writer = new FileWriter(jsonFile)) {
            writer.write("{\"user\": {\"name\": \"John\", \"address\": {\"city\": \"NYC\"}}}");
        }
        
        Object result = parser.parse(jsonFile);
        
        assertNotNull(result);
        assertInstanceOf(Map.class, result);
        
        @SuppressWarnings("unchecked")
        Map<String, Object> map = (Map<String, Object>) result;
        assertTrue(map.containsKey("user"));
        
        @SuppressWarnings("unchecked")
        Map<String, Object> user = (Map<String, Object>) map.get("user");
        assertEquals("John", user.get("name"));
        assertTrue(user.containsKey("address"));
    }
    
    @Test
    void testParseInvalidJson() throws IOException {
        // Create invalid JSON file
        File jsonFile = new File(tempDir, "invalid.json");
        try (FileWriter writer = new FileWriter(jsonFile)) {
            writer.write("{\"name\": \"John\", \"age\": }");
        }
        
        ParsingException exception = assertThrows(ParsingException.class, () -> parser.parse(jsonFile));
        assertTrue(exception.getMessage().contains("Failed to parse JSON file"));
    }
    
    @Test
    void testParseNonExistentFile() {
        File nonExistentFile = new File(tempDir, "nonexistent.json");
        
        ParsingException exception = assertThrows(ParsingException.class, () -> parser.parse(nonExistentFile));
        assertTrue(exception.getMessage().contains("Failed to parse JSON file"));
    }
}
