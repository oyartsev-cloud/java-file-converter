package com.fileconverter.utils;

/**
 * Enumeration of supported file formats.
 */
public enum FileFormat {
    JSON("json", "application/json"),
    XML("xml", "application/xml"),
    CSV("csv", "text/csv");
    
    private final String extension;
    private final String mimeType;
    
    FileFormat(String extension, String mimeType) {
        this.extension = extension;
        this.mimeType = mimeType;
    }
    
    public String getExtension() {
        return extension;
    }
    
    public String getMimeType() {
        return mimeType;
    }
    
    /**
     * Detect format from file extension.
     */
    public static FileFormat fromExtension(String extension) {
        if (extension == null) {
            return null;
        }
        
        String normalizedExt = extension.toLowerCase();
        if (normalizedExt.startsWith(".")) {
            normalizedExt = normalizedExt.substring(1);
        }
        
        for (FileFormat format : values()) {
            if (format.extension.equals(normalizedExt)) {
                return format;
            }
        }
        
        return null;
    }
    
    /**
     * Detect format from file path.
     */
    public static FileFormat fromFilePath(String filePath) {
        if (filePath == null) {
            return null;
        }
        
        int lastDotIndex = filePath.lastIndexOf('.');
        if (lastDotIndex == -1 || lastDotIndex == filePath.length() - 1) {
            return null;
        }
        
        String extension = filePath.substring(lastDotIndex + 1);
        return fromExtension(extension);
    }
}
