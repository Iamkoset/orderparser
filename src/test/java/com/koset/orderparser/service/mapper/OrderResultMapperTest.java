package com.koset.orderparser.service.mapper;

import com.koset.orderparser.model.Order;
import com.koset.orderparser.model.OrderReadingResult;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;


class OrderResultMapperTest {

    OrderResultMapper resultMapper = new OrderResultMapper();

    @Test
    void mapTestSuccess() {
        Order order = Order.builder()
                .amount("42")
                .orderId("42")
                .currency("RUR")
                .comment("SUCH COMMENT, MUCH RESULT")
                .build();

        OrderReadingResult result = resultMapper.map(1, order, "test.json", 42);

        assertEquals(1, result.getId());
        assertEquals("42", result.getOrder().getAmount());
        assertEquals("42", result.getOrder().getOrderId());
        assertEquals("RUR", result.getOrder().getCurrency());
        assertEquals("SUCH COMMENT, MUCH RESULT", result.getOrder().getComment());
        assertEquals("test.json", result.getFilename());
        assertEquals(42, result.getLine());
    }

    @ParameterizedTest
    @MethodSource("provideOrderResultMassage")
    void mapTestResultString(Order order, String expectedResultMessage) {
        OrderReadingResult result = resultMapper.map(1, order, "test.json", 42);
        assertEquals(expectedResultMessage, result.getResult());
    }

    private static Stream<Arguments> provideOrderResultMassage() {
        return Stream.of(
                Arguments.of(
                        Order.builder().build(),
                        "amount is not specified, orderId is not specified, currency is not specified"
                ),
                Arguments.of(
                        Order.builder().amount("42").build(),
                        "orderId is not specified, currency is not specified"
                ),
                Arguments.of(
                        Order.builder().orderId("42").build(),
                        "amount is not specified, currency is not specified"
                ),
                Arguments.of(
                        Order.builder().currency("RUR").build(),
                        "amount is not specified, orderId is not specified"
                ),
                Arguments.of(
                        Order.builder().amount("42").orderId("42").build(),
                        "currency is not specified"
                ),
                Arguments.of(
                        Order.builder().amount("42").currency("RUR").build(),
                        "orderId is not specified"
                ),
                Arguments.of(
                        Order.builder().orderId("42").currency("RUR").build(),
                        "amount is not specified"
                ),
                Arguments.of(
                        Order.builder().amount("42").orderId("42").currency("RUR").build(),
                        "OK"
                ),
                Arguments.of(
                        Order.builder()
                                .amount("not number")
                                .orderId("not number")
                                .currency("not currency")
                                .build(),
                        "amount is not specified, orderId is not specified, currency is not specified"
                )
        );
    }
}