package com.koset.orderparser.service.reader;

import com.koset.orderparser.model.FileData;
import com.koset.orderparser.model.FileFormat;
import com.koset.orderparser.model.OrderReadingResult;
import com.koset.orderparser.service.exception.ServiceException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class OrderReaderDelegate {

    private final Map<FileFormat, FormatReaderStrategy<List<OrderReadingResult>>> formatStrategies;

    public OrderReaderDelegate(List<FormatReaderStrategy<List<OrderReadingResult>>> strategies) {
        formatStrategies = strategies.stream()
                .collect(
                        Collectors.toMap(
                                FormatReaderStrategy::getSupportedFormat,
                                Function.identity()
                        )
                );
    }

    public List<OrderReadingResult> read(FileData fileData) {
        try {
            return formatStrategies.get(fileData.getFileFormat())
                    .read(fileData.getFilePath(), fileData.getFileFormat());
        } catch (Exception e) {
            throw new ServiceException("Failed to read fileData: " + fileData, e);
        }
    }

}
