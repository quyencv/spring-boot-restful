package com.tutorial.utils;

public class EscapeUtils {
    
    public static String escapeStringForMySQL(String txt) {
        String resutl = txt.replaceAll("\\", "\\\\").replaceAll("\b", "\\b").replaceAll("\n", "\\n")
                .replaceAll("\r", "\\r").replaceAll("\t", "\\t").replaceAll("\\x1A", "\\Z").replaceAll("\\x00", "\\0")
                .replaceAll("'", "\\'").replaceAll("\"", "\\\"");
        return resutl;
    }
    
    public static String escapeWildcardsForMySQL(String txt) {
        String resutl = escapeStringForMySQL(txt).replaceAll("%", "\\%").replaceAll("_", "\\_");
        return resutl;
    }
}
