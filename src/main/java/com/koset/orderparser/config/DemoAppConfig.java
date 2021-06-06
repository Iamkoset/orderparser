package com.koset.orderparser.config;

import com.koset.orderparser.model.Order;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
public class DemoAppConfig {

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean
    public ExecutorService readerExecutor(ExecutorProperties properties) {
        return Executors.newFixedThreadPool(properties.getReaderThreadsCount());
    }

    @Bean
    public ExecutorService writerExecutor(ExecutorProperties properties) {
        return Executors.newFixedThreadPool(properties.getWriterThreadsCount());
    }

    @Bean
    public ColumnPositionMappingStrategy<Order> csvPositionMappingStrategy() {
        ColumnPositionMappingStrategy<Order> strategy = new ColumnPositionMappingStrategy<>();
        strategy.setType(Order.class);
        String[] fields = {"orderId", "amount", "currency", "comment"};
        strategy.setColumnMapping(fields);

        return strategy;
    }

}
