package com.koset.orderparser.util;

import com.koset.orderparser.model.Currency;
import lombok.experimental.UtilityClass;

import java.math.BigDecimal;

@UtilityClass
public class ValueParser {

    public static Number parseNumber(String value) {
        Number number = null;
        try {
            number = new BigDecimal(value);
        } catch (Exception e) {
            // ignored, in case of incorrect string we'll return null
        }

        return number;
    }

    public static Currency parseCurrency(String value) {
        Currency currency = null;
        try {
            currency = Currency.fromString(value);
        } catch (Exception e) {
            // ignored, in case of incorrect string we'll return null
        }

        return currency;
    }

}
