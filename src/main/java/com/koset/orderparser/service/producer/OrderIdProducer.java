package com.koset.orderparser.service.producer;

import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

@Component
public class OrderIdProducer {

    private final AtomicInteger idCounter = new AtomicInteger(1);

    public int getId() {
        return idCounter.getAndIncrement();
    }

}
