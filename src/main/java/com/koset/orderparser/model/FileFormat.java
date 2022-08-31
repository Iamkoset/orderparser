package com.koset.orderparser.model;

import com.koset.orderparser.service.exception.ServiceException;

public enum FileFormat {

    CSV,
    JSON,
    NDJSON,
    XLSX;

    public static FileFormat fromString(String extension) {
        for (FileFormat fileFormat : FileFormat.values()) {
            if (fileFormat.name().equalsIgnoreCase(extension)) {
                return fileFormat;
            }
        }
        throw new ServiceException("Unsupported format: " + extension);
    }

}
