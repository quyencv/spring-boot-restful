package com.tutorial.utils;

public class StringUtils {

    public static String stripAccents(String input) {
        String result = org.apache.commons.lang3.StringUtils.stripAccents(input);
        result = result.replace("đ", "d").replace("Đ", "D");
        return result;
    }
}
