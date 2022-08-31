package com.koset.orderparser.service.reader.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.koset.orderparser.model.FileFormat;
import com.koset.orderparser.model.Order;
import com.koset.orderparser.model.OrderReadingResult;
import com.koset.orderparser.service.exception.ServiceException;
import com.koset.orderparser.service.mapper.OrderResultMapper;
import com.koset.orderparser.service.producer.OrderIdProducer;
import com.koset.orderparser.service.reader.FormatReaderStrategy;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.koset.orderparser.util.JsonUtils;
import lombok.RequiredArgsConstructor;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static java.nio.file.Files.newBufferedReader;

@RequiredArgsConstructor
abstract class AbstractJsonFormatOrderReaderStrategy implements FormatReaderStrategy<List<OrderReadingResult>> {

    private final ObjectMapper objectMapper;

    private final OrderIdProducer idProducer;

    private final OrderResultMapper resultMapper;

    @Override
    public List<OrderReadingResult> read(Path filePath, FileFormat fileFormat) throws IOException {

        if (fileFormat != getSupportedFormat()) {
            throw new ServiceException("Unsupported file format: " + fileFormat);
        }

        try (BufferedReader reader = newBufferedReader(filePath, StandardCharsets.UTF_8)) {
            List<OrderReadingResult> readingResults = new ArrayList<>();
            int line = 1;

            String currentLine;
            while ((currentLine = reader.readLine()) != null) {
                if (currentLine.isEmpty()) {
                    continue;
                }

                Order order = readOrder(currentLine);

                String fileName = filePath.getFileName().toString();
                OrderReadingResult readingResult =
                        resultMapper.map(idProducer.getId(), order, fileName, line);

                readingResults.add(readingResult);

                line += 1;
            }

            return readingResults;
        }
    }

    private Order readOrder(String currentLine) {
        Order order = null;
        try {
            order = objectMapper.readValue(
                    JsonUtils.normalizeQuotes(currentLine),
                    Order.class
            );
        } catch (JsonProcessingException e) {
            // ignore
        }

        return order;
    }

}
