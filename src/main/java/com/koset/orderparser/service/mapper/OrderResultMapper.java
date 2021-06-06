package com.koset.orderparser.service.mapper;

import com.koset.orderparser.model.Currency;
import com.koset.orderparser.model.Order;
import com.koset.orderparser.model.OrderReadingResult;
import com.koset.orderparser.util.ValueParser;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class OrderResultMapper {

    private static final String ORDER_IS_NOT_PRESENT = "order is not present";

    private static final String AMOUNT_NOT_SPECIFIED = "amount is not specified";
    private static final String CURRENCY_NOT_SPECIFIED = "currency is not specified";
    private static final String ORDER_ID_NOT_SPECIFIED = "orderId is not specified";

    private static final String OK = "OK";

    public OrderReadingResult map(int id, Order order, String fileName, int line) {
        return OrderReadingResult.builder()
                .id(id)
                .order(order)
                .line(line)
                .filename(fileName)
                .result(buildResultMessage(order))
                .build();
    }

    private String buildResultMessage(Order order) {
        if (order == null) {
            return ORDER_IS_NOT_PRESENT;
        }

        Number amount = ValueParser.parseNumber(order.getAmount());
        Number orderId = ValueParser.parseNumber(order.getOrderId());
        Currency currency = ValueParser.parseCurrency(order.getCurrency());

        return buildResultMessage(amount, orderId, currency);
    }

    private String buildResultMessage(Number amount, Number orderId, Currency currency) {
        if (amount != null && orderId != null && currency != null) {
            return OK;
        }

        List<String> errorMessages = new ArrayList<>();
        if (amount == null) {
            errorMessages.add(AMOUNT_NOT_SPECIFIED);
        }
        if (orderId == null) {
            errorMessages.add(ORDER_ID_NOT_SPECIFIED);
        }
        if (currency == null) {
            errorMessages.add(CURRENCY_NOT_SPECIFIED);
        }

        return String.join(", ", errorMessages);
    }

}
