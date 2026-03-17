package com.fileconverter.generator;

import com.opencsv.CSVWriter;
import com.fileconverter.exception.FileConverterException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * CSV generator implementation using OpenCSV.
 */
public class CsvGenerator implements Generator {
    
    @Override
    public void generate(Object data, File file) throws FileConverterException {
        try (CSVWriter writer = new CSVWriter(new FileWriter(file))) {
            
            if (data == null) {
                writer.writeNext(new String[]{});
                return;
            }
            
            if (data instanceof List) {
                List<?> dataList = (List<?>) data;
                
                if (dataList.isEmpty()) {
                    writer.writeNext(new String[]{});
                    return;
                }
                
                // Check if it's a list of maps
                if (dataList.get(0) instanceof Map) {
                    writeListOfMaps(writer, dataList);
                } else {
                    // List of primitive values - single column
                    writeListOfPrimitives(writer, dataList);
                }
                
            } else if (data instanceof Map) {
                // Single map - write as single row
                writeSingleMap(writer, (Map<?, ?>) data);
            } else {
                // Single value - single column
                writer.writeNext(new String[]{"value"});
                writer.writeNext(new String[]{String.valueOf(data)});
            }
            
        } catch (IOException e) {
            throw new FileConverterException("Failed to generate CSV file: " + e.getMessage(), e);
        }
    }
    
    @SuppressWarnings("unchecked")
    private void writeListOfMaps(CSVWriter writer, List<?> dataList) {
        List<Map<String, Object>> maps = (List<Map<String, Object>>) dataList;
        
        if (maps.isEmpty()) {
            return;
        }
        
        // Get all possible keys from all maps
        List<String> headers = new ArrayList<>();
        for (Map<String, Object> map : maps) {
            for (String key : map.keySet()) {
                if (!headers.contains(key)) {
                    headers.add(key);
                }
            }
        }
        
        // Write headers
        writer.writeNext(headers.toArray(new String[0]));
        
        // Write data rows
        for (Map<String, Object> map : maps) {
            String[] row = new String[headers.size()];
            for (int i = 0; i < headers.size(); i++) {
                Object value = map.get(headers.get(i));
                row[i] = value != null ? String.valueOf(value) : "";
            }
            writer.writeNext(row);
        }
    }
    
    private void writeListOfPrimitives(CSVWriter writer, List<?> dataList) {
        writer.writeNext(new String[]{"value"});
        
        for (Object item : dataList) {
            writer.writeNext(new String[]{String.valueOf(item)});
        }
    }
    
    private void writeSingleMap(CSVWriter writer, Map<?, ?> map) {
        if (map.isEmpty()) {
            writer.writeNext(new String[]{});
            return;
        }
        
        String[] headers = map.keySet().stream()
                .map(key -> String.valueOf(key))
                .toArray(String[]::new);
        
        String[] values = map.values().stream()
                .map(value -> value != null ? String.valueOf(value) : "")
                .toArray(String[]::new);
        
        writer.writeNext(headers);
        writer.writeNext(values);
    }
}
