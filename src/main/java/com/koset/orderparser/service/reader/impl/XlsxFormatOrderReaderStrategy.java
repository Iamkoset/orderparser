package com.koset.orderparser.service.reader.impl;

import com.koset.orderparser.model.FileFormat;
import com.koset.orderparser.model.Order;
import com.koset.orderparser.service.reader.FormatReaderStrategy;
import org.apache.commons.lang3.NotImplementedException;

import java.nio.file.Path;

// @Component
class XlsxFormatOrderReaderStrategy implements FormatReaderStrategy<Order> {

    // to extend app with xlsx file format, implement this method, and uncomment
    // @Component annotation, to register class as Spring bean
    @Override
    public Order read(Path filePath, FileFormat fileFormat) {
        throw new NotImplementedException("Welcome to the club buddy!");
    }

    @Override
    public FileFormat getSupportedFormat() {
        return FileFormat.XLSX;
    }

}
