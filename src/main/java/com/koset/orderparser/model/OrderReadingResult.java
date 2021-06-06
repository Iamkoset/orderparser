package com.koset.orderparser.model;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class OrderReadingResult {

    int id;

    @JsonUnwrapped
    Order order;

    String filename;

    int line;

    String result;

}
