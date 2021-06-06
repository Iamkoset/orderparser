package com.koset.orderparser.service.reader.impl;

import com.koset.orderparser.model.FileFormat;
import com.koset.orderparser.service.mapper.OrderResultMapper;
import com.koset.orderparser.service.producer.OrderIdProducer;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

@Component
class JsonFormatOrderReaderStrategy extends AbstractJsonFormatOrderReaderStrategy {

    public JsonFormatOrderReaderStrategy(
            ObjectMapper objectMapper,
            OrderIdProducer idProducer,
            OrderResultMapper resultMapper
    ) {
        super(objectMapper, idProducer, resultMapper);
    }

    @Override
    public FileFormat getSupportedFormat() {
        return FileFormat.JSON;
    }

}
