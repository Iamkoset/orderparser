package com.koset.orderparser.service.reader;

import com.koset.orderparser.model.FileFormat;

import java.io.IOException;
import java.nio.file.Path;

public interface FormatReaderStrategy<T> {

    T read(Path filePath, FileFormat fileFormat) throws IOException;

    FileFormat getSupportedFormat();

}
