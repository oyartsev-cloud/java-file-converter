package com.fileconverter.parser;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import com.fileconverter.exception.ParsingException;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * CSV parser implementation using OpenCSV.
 */
public class CsvParser implements Parser {
    
    @Override
    public Object parse(File file) throws ParsingException {
        try (CSVReader reader = new CSVReader(new FileReader(file))) {
            List<String[]> records = reader.readAll();
            
            if (records.isEmpty()) {
                return new ArrayList<>();
            }
            
            String[] headers = records.get(0);
            List<Map<String, String>> result = new ArrayList<>();
            
            for (int i = 1; i < records.size(); i++) {
                String[] record = records.get(i);
                Map<String, String> row = new HashMap<>();
                
                for (int j = 0; j < headers.length && j < record.length; j++) {
                    row.put(headers[j], record[j]);
                }
                
                // Handle case where record has more fields than headers
                for (int j = headers.length; j < record.length; j++) {
                    row.put("column_" + (j + 1), record[j]);
                }
                
                result.add(row);
            }
            
            return result;
            
        } catch (IOException | CsvException e) {
            throw new ParsingException("Failed to parse CSV file: " + e.getMessage(), e);
        }
    }
}
