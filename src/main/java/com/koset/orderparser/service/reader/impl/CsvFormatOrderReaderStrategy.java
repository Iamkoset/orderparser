package com.koset.orderparser.service.reader.impl;

import com.koset.orderparser.model.FileFormat;
import com.koset.orderparser.model.Order;
import com.koset.orderparser.model.OrderReadingResult;
import com.koset.orderparser.service.exception.ServiceException;
import com.koset.orderparser.service.mapper.OrderResultMapper;
import com.koset.orderparser.service.producer.OrderIdProducer;
import com.koset.orderparser.service.reader.FormatReaderStrategy;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static java.nio.file.Files.newBufferedReader;

@Component
@RequiredArgsConstructor
class CsvFormatOrderReaderStrategy implements FormatReaderStrategy<List<OrderReadingResult>> {

    private final ColumnPositionMappingStrategy<Order> csvPositionMappingStrategy;

    private final OrderIdProducer idProducer;

    private final OrderResultMapper resultMapper;

    @Override
    public List<OrderReadingResult> read(Path filePath, FileFormat fileFormat) throws IOException {

        if (fileFormat != FileFormat.CSV) {
            throw new ServiceException("Unsupported file format: " + fileFormat);
        }

        try (BufferedReader reader = newBufferedReader(filePath, StandardCharsets.UTF_8)) {
            CsvToBean<Order> csvToBean = new CsvToBeanBuilder<Order>(reader)
                    .withMappingStrategy(csvPositionMappingStrategy)
                    .withIgnoreLeadingWhiteSpace(true)
                    .withIgnoreEmptyLine(true)
                    .build();

            List<OrderReadingResult> readingResults = new ArrayList<>();
            int line = 1;
            for (Order order : csvToBean) {

                String fileName = filePath.getFileName().toString();
                OrderReadingResult readingResult =
                        resultMapper.map(idProducer.getId(), order, fileName, line);

                readingResults.add(readingResult);

                line += 1;
            }

            return readingResults;
        }
    }

    @Override
    public FileFormat getSupportedFormat() {
        return FileFormat.CSV;
    }

}
