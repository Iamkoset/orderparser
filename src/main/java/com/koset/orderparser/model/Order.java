package com.koset.orderparser.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.koset.orderparser.model.serialize.StringToNumberSerializer;
import com.opencsv.bean.CsvBindByPosition;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Order {

    @CsvBindByPosition(position = 0)
    @JsonSerialize(using = StringToNumberSerializer.class)
    String orderId;

    @CsvBindByPosition(position = 1)
    @JsonSerialize(using = StringToNumberSerializer.class)
    String amount;

    @CsvBindByPosition(position = 2)
    String currency;

    @CsvBindByPosition(position = 3)
    String comment;

}
