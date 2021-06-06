package com.koset.orderparser.model;

import com.koset.orderparser.service.exception.ServiceException;

public enum Currency {

    USD, EUR, RUR;

    public static Currency fromString(String value) {
        for (Currency currency : Currency.values()) {
            if (currency.name().equalsIgnoreCase(value)) {
                return currency;
            }
        }
        throw new ServiceException("Unsupported currency: " + value);
    }
}
