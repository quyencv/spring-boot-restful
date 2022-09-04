package com.tutorial.config.handle;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    private final Logger logger = LoggerFactory.getLogger(CustomAccessDeniedHandler.class);

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
            AccessDeniedException accessDeniedException) throws IOException, ServletException {

        logger.debug("CustomAccessDeniedHandler.handle request: {}", request);
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);

        Map<String, Object> responseMapValue = new HashMap<>();
        responseMapValue.put("status", HttpServletResponse.SC_FORBIDDEN);
        responseMapValue.put("message", "You don't have required role to perform this action.");

        OutputStream out = response.getOutputStream();
        objectMapper.writerWithDefaultPrettyPrinter().writeValue(out, responseMapValue);
        out.flush();

        logger.debug("CustomAccessDeniedHandler.handle: response {}", responseMapValue);
    }

}
