package com.fileconverter.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FileFormatTest {
    
    @Test
    void testFromExtension() {
        assertEquals(FileFormat.JSON, FileFormat.fromExtension("json"));
        assertEquals(FileFormat.XML, FileFormat.fromExtension("xml"));
        assertEquals(FileFormat.CSV, FileFormat.fromExtension("csv"));
        
        // Test with dot
        assertEquals(FileFormat.JSON, FileFormat.fromExtension(".json"));
        assertEquals(FileFormat.XML, FileFormat.fromExtension(".xml"));
        assertEquals(FileFormat.CSV, FileFormat.fromExtension(".csv"));
        
        // Test case insensitive
        assertEquals(FileFormat.JSON, FileFormat.fromExtension("JSON"));
        assertEquals(FileFormat.XML, FileFormat.fromExtension("Xml"));
        assertEquals(FileFormat.CSV, FileFormat.fromExtension("CSV"));
        
        // Test unsupported formats
        assertNull(FileFormat.fromExtension("txt"));
        assertNull(FileFormat.fromExtension("pdf"));
        assertNull(FileFormat.fromExtension(""));
        assertNull(FileFormat.fromExtension(null));
    }
    
    @Test
    void testFromFilePath() {
        assertEquals(FileFormat.JSON, FileFormat.fromFilePath("test.json"));
        assertEquals(FileFormat.XML, FileFormat.fromFilePath("path/to/file.xml"));
        assertEquals(FileFormat.CSV, FileFormat.fromFilePath("C:\\data\\file.csv"));
        
        // Test with multiple dots
        assertEquals(FileFormat.JSON, FileFormat.fromFilePath("test.config.json"));
        assertEquals(FileFormat.XML, FileFormat.fromFilePath("data.v2.xml"));
        
        // Test unsupported formats
        assertNull(FileFormat.fromFilePath("test.txt"));
        assertNull(FileFormat.fromFilePath("README"));
        assertNull(FileFormat.fromFilePath("file."));
        assertNull(FileFormat.fromFilePath(""));
        assertNull(FileFormat.fromFilePath(null));
    }
    
    @Test
    void testGetExtension() {
        assertEquals("json", FileFormat.JSON.getExtension());
        assertEquals("xml", FileFormat.XML.getExtension());
        assertEquals("csv", FileFormat.CSV.getExtension());
    }
    
    @Test
    void testGetMimeType() {
        assertEquals("application/json", FileFormat.JSON.getMimeType());
        assertEquals("application/xml", FileFormat.XML.getMimeType());
        assertEquals("text/csv", FileFormat.CSV.getMimeType());
    }
}
