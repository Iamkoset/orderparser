package com.koset.orderparser.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class JsonUtils {

    public static String normalizeQuotes(String jsonString) {
        return jsonString.replaceAll("([“”])", "\"");
    }

}
