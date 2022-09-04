package com.tutorial.config.provide;

import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;

import com.tutorial.dto.UserDTO;
import com.tutorial.service.UserService;

@Component
public class CustomAuthProvider implements AuthenticationProvider {

    private final Logger logger = LoggerFactory.getLogger(CustomAuthProvider.class);

    @Autowired
    private UserService userService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        logger.debug("CustomAuthProvider.authenticate authentication: {}", authentication);

        if (StringUtils.isEmpty(authentication.getName()) || Objects.isNull(authentication.getCredentials())) {
            throw new UsernameNotFoundException("Username or password is incorrect");
        }

        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        Optional<UserDTO> userDTOOpt = userService.findByUsername(username);

        if (userDTOOpt.isPresent() && BCrypt.checkpw(password, userDTOOpt.get().getPassword())) {
            Collection<GrantedAuthority> grantedAuthorities = getGrantedAuthorities(userDTOOpt.get());
            return new CustomAuthToken(
                    new org.springframework.security.core.userdetails.User(username, password, grantedAuthorities),
                    password, userDTOOpt.get(), grantedAuthorities);
        } else {
            throw new UsernameNotFoundException(username + " [Username or password is incorrect.]");
        }
    }

    public Collection<GrantedAuthority> getGrantedAuthorities(UserDTO userDTO) {
        return Arrays.asList(userDTO.getRole().name()).stream().map(item -> "ROLE_" + item)
                .map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        logger.debug("CustomAuthProvider.supports authentication: {}", authentication);
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }
}
