package com.tutorial.config.jwt;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class CustomAuthEntryPoint implements AuthenticationEntryPoint {
    private final Logger logger = LoggerFactory.getLogger(CustomAuthEntryPoint.class);

    private final ObjectMapper objectMapper;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException authException) throws IOException, ServletException {

        logger.debug("CustomAuthEntryPoint.commence request: {}", request.getRequestURI());
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        Map<String, Object> responseMapValue = new HashMap<>();
        responseMapValue.put("status", HttpServletResponse.SC_UNAUTHORIZED);
        responseMapValue.put("message", "You need to login first in order to perform this action.");

        OutputStream out = response.getOutputStream();
        objectMapper.writerWithDefaultPrettyPrinter().writeValue(out, responseMapValue);
        out.flush();
        logger.error("CustomAuthEntryPoint.commence response: {}", responseMapValue);
    }
}
