package com.tutorial.utils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

public class JsonUtils {

    public static <T> Optional<T> servletRequestToObject(HttpServletRequest request, Class<T> clazz) {
        try {
            String jsonString = IOUtils.toString(request.getInputStream(), StandardCharsets.UTF_8);
            Optional<T> result = convertToObject(clazz, jsonString);
            return result;
        } catch (IOException e) {
            return Optional.empty();
        }
    }

    public static String convertToJSON(Object object) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectWriter objWriter = objectMapper.writer().withDefaultPrettyPrinter();
        try {
            return objWriter.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            return StringUtils.EMPTY;
        }
    }

    public static <T> Optional<T> convertToObject(Class<T> clazz, String jsonString) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return Optional.ofNullable(objectMapper.readValue(jsonString, clazz));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public static <T> Optional<T> clone(T object, Class<T> clazz) {
        String jsonString = convertToJSON(object);
        return convertToObject(clazz, jsonString);
    }
}
