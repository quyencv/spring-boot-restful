package com.tutorial.utils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class JsonUtils {

    private final ObjectMapper objectMapper;

    public <T> Optional<T> servletRequestToObject(HttpServletRequest request, Class<T> clazz) {
        try {
            String jsonString = IOUtils.toString(request.getInputStream(), StandardCharsets.UTF_8);
            Optional<T> result = convertToObject(clazz, jsonString);
            return result;
        } catch (IOException e) {
            return Optional.empty();
        }
    }

    public String convertToJSON(Object object) {
        ObjectWriter objWriter = objectMapper.writer().withDefaultPrettyPrinter();
        try {
            return objWriter.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            return StringUtils.EMPTY;
        }
    }

    public <T> Optional<T> convertToObject(Class<T> clazz, String jsonString) {
        try {
            return Optional.ofNullable(objectMapper.readValue(jsonString, clazz));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public <T> Optional<T> clone(T object, Class<T> clazz) {
        String jsonString = convertToJSON(object);
        return convertToObject(clazz, jsonString);
    }
}
