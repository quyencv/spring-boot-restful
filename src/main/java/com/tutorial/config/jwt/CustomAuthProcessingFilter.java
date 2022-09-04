package com.tutorial.config.jwt;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;

import com.tutorial.config.provide.CustomAuthToken;
import com.tutorial.dto.UserDTO;
import com.tutorial.utils.JsonUtils;

/**
 * Abstract processor of browser-based HTTP-based authentication requests. json web token on submit singin
 */
public class CustomAuthProcessingFilter extends AbstractAuthenticationProcessingFilter {

    private final Logger logger = LoggerFactory.getLogger(CustomAuthProcessingFilter.class);

    public CustomAuthProcessingFilter(RequestMatcher requestMatcher, AuthenticationManager authManager) {
        super(requestMatcher);
        setAuthenticationManager(authManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException, IOException, ServletException {
        CustomAuthToken authRequest = getAuthRequest(request);
        authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
        return getAuthenticationManager().authenticate(authRequest);
    }

    @Override
    protected AuthenticationManager getAuthenticationManager() {
        return super.getAuthenticationManager();
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
            Authentication authResult) throws IOException, ServletException {
        logger.info("CustomAuthProcessingFilter.successfulAuthentication");
        super.successfulAuthentication(request, response, chain, authResult);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException failed) throws IOException, ServletException {
        logger.info("CustomAuthProcessingFilter.unsuccessfulAuthentication");
        super.unsuccessfulAuthentication(request, response, failed);
    }

    public CustomAuthToken getAuthRequest(HttpServletRequest request) {
        logger.debug("CustomAuthProcessingFilter.getAuthRequest {}", request);

        Optional<UserDTO> userDTOOpt = JsonUtils.servletRequestToObject(request, UserDTO.class);
        if (!userDTOOpt.isPresent()) {
            return new CustomAuthToken(StringUtils.EMPTY, StringUtils.EMPTY, new UserDTO());
        }

        String username = StringUtils.isEmpty(userDTOOpt.get().getUsername()) ? StringUtils.EMPTY
                : userDTOOpt.get().getUsername();
        String password = StringUtils.isEmpty(userDTOOpt.get().getPassword()) ? StringUtils.EMPTY
                : userDTOOpt.get().getPassword();

        logger.debug("CustomAuthProcessingFilter getAuthRequest: username: {} /password: {}", username, password);
        return new CustomAuthToken(username, password, userDTOOpt.get());
    }
}
