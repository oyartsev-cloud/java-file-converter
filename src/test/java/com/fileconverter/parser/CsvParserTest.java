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

class CsvParserTest {
    
    private CsvParser parser;
    
    @TempDir
    File tempDir;
    
    @BeforeEach
    void setUp() {
        parser = new CsvParser();
    }
    
    @Test
    void testParseSimpleCsv() throws IOException, ParsingException {
        // Create test CSV file
        File csvFile = new File(tempDir, "test.csv");
        try (FileWriter writer = new FileWriter(csvFile)) {
            writer.write("name,age\n");
            writer.write("John,30\n");
            writer.write("Jane,25\n");
        }
        
        Object result = parser.parse(csvFile);
        
        assertNotNull(result);
        assertInstanceOf(List.class, result);
        
        @SuppressWarnings("unchecked")
        List<Map<String, String>> list = (List<Map<String, String>>) result;
        assertEquals(2, list.size());
        
        Map<String, String> firstRow = list.get(0);
        assertEquals("John", firstRow.get("name"));
        assertEquals("30", firstRow.get("age"));
        
        Map<String, String> secondRow = list.get(1);
        assertEquals("Jane", secondRow.get("name"));
        assertEquals("25", secondRow.get("age"));
    }
    
    @Test
    void testParseEmptyCsv() throws IOException, ParsingException {
        // Create empty CSV file
        File csvFile = new File(tempDir, "empty.csv");
        try (FileWriter writer = new FileWriter(csvFile)) {
            writer.write("");
        }
        
        Object result = parser.parse(csvFile);
        
        assertNotNull(result);
        assertInstanceOf(List.class, result);
        
        List<?> list = (List<?>) result;
        assertTrue(list.isEmpty());
    }
    
    @Test
    void testParseCsvWithMoreColumnsThanHeaders() throws IOException, ParsingException {
        // Create CSV file with extra columns
        File csvFile = new File(tempDir, "extra.csv");
        try (FileWriter writer = new FileWriter(csvFile)) {
            writer.write("name,age\n");
            writer.write("John,30,New York\n");
            writer.write("Jane,25,Los Angeles\n");
        }
        
        Object result = parser.parse(csvFile);
        
        assertNotNull(result);
        assertInstanceOf(List.class, result);
        
        @SuppressWarnings("unchecked")
        List<Map<String, String>> list = (List<Map<String, String>>) result;
        assertEquals(2, list.size());
        
        Map<String, String> firstRow = list.get(0);
        assertEquals("John", firstRow.get("name"));
        assertEquals("30", firstRow.get("age"));
        assertEquals("New York", firstRow.get("column_3"));
    }
    
    @Test
    void testParseCsvWithMissingValues() throws IOException, ParsingException {
        // Create CSV file with missing values
        File csvFile = new File(tempDir, "missing.csv");
        try (FileWriter writer = new FileWriter(csvFile)) {
            writer.write("name,age,city\n");
            writer.write("John,,New York\n");
            writer.write("Jane,25,\n");
        }
        
        Object result = parser.parse(csvFile);
        
        assertNotNull(result);
        assertInstanceOf(List.class, result);
        
        @SuppressWarnings("unchecked")
        List<Map<String, String>> list = (List<Map<String, String>>) result;
        assertEquals(2, list.size());
        
        Map<String, String> firstRow = list.get(0);
        assertEquals("John", firstRow.get("name"));
        assertEquals("", firstRow.get("age"));
        assertEquals("New York", firstRow.get("city"));
    }
    
    @Test
    void testParseNonExistentFile() {
        File nonExistentFile = new File(tempDir, "nonexistent.csv");
        
        ParsingException exception = assertThrows(ParsingException.class, () -> parser.parse(nonExistentFile));
        assertTrue(exception.getMessage().contains("Failed to parse CSV file"));
    }
}
