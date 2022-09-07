package com.tutorial.config.handle;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.tutorial.config.jwt.JwtUtils;
import com.tutorial.dto.UserDTO;
import com.tutorial.service.UserService;
import com.tutorial.utils.JsonUtils;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class CustomAuthSuccessHandler implements AuthenticationSuccessHandler {

    private final Logger logger = LoggerFactory.getLogger(CustomAuthSuccessHandler.class);

    private final JwtUtils jwtUtils;
    
    private final JsonUtils jsonUtils;

    private final UserService userService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {

        logger.debug("CustomAuthSuccessHandler.onAuthenticationSuccess request: {}", request.getRequestURI());

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.toString());

        // Write Authorization to Headers of Response.
        jwtUtils.addAuthentication(response, authentication.getName());
        UserDTO userDTO = userService.findByUsername(authentication.getName()).get();
        userDTO.setToken(response.getHeader(JwtUtils.HEADER_STRING));

        // Get the printwriter object from response to write the required json object to the output stream
        PrintWriter out = response.getWriter();
        String jsonObject = jsonUtils.convertToJSON(userDTO);
        // Assuming your json object is **jsonObject**, perform the following, it will return your json object
        out.print(jsonObject);
        out.flush();
        logger.debug("CustomAuthSuccessHandler.onAuthenticationSuccess response: {}", jsonObject);

    }

}
