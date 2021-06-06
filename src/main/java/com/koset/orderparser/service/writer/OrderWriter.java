package com.koset.orderparser.service.writer;

import com.koset.orderparser.model.OrderReadingResult;
import com.koset.orderparser.service.exception.ServiceException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class OrderWriter {

    private final ObjectMapper objectMapper;

    public void write(List<OrderReadingResult> orderReadingResults) {
        for (OrderReadingResult result : orderReadingResults) {
            try {
                System.out.println(objectMapper.writeValueAsString(result));
            } catch (JsonProcessingException e) {
                throw new ServiceException("Failed to write object as JSON string: " + result, e);
            }
        }
    }

}
