package com.tutorial.config.jwt;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import com.tutorial.config.provide.CustomAuthProvider;
import com.tutorial.config.provide.CustomAuthToken;
import com.tutorial.dto.UserDTO;
import com.tutorial.service.UserService;
import com.tutorial.utils.DateUtils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Service
public class JwtUtils {

    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    public static final long REFRESH_EXPIRATION_TIME = 10 * TimeUnit.DAYS.toMillis(1); // 10 days

    public static final long EXPIRATION_TIME = 1 * TimeUnit.DAYS.toMillis(1); // 1 day

    private static final String JWT_SECRET = "ManageItemASecretKey";

    public static final String TOKEN_PREFIX = "Bearer";

    public static final String HEADER_STRING = "Authorization";

    @Autowired
    private UserService userService;

    @Autowired
    private CustomAuthProvider customAuthProvider;

    public void addAuthentication(HttpServletResponse res, String username) {
        String jwt = generateToken(username, EXPIRATION_TIME);
        res.addHeader(HEADER_STRING, jwt);
    }

    public String generateToken(String username, long expiration) {
        String jwt = Jwts.builder().setSubject(username).setIssuedAt(DateUtils.asDate(LocalDate.now()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(SignatureAlgorithm.HS512, JWT_SECRET).compact();
        return jwt;
    }

    public Authentication getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(HEADER_STRING);
        // parse the token.
        Optional<Jws<Claims>> jwsToken = getJwtToken(token);
        if (jwsToken.isPresent()) {
            String username = jwsToken.get().getBody().getSubject();
            Optional<UserDTO> userDTOOpt = userService.findByUsername(username);
            if (userDTOOpt.isPresent()) {
                Collection<GrantedAuthority> grantedAuthorities = customAuthProvider.getGrantedAuthorities(userDTOOpt.get());
                return new CustomAuthToken(username, null, null, grantedAuthorities);
            } else {
                return null;
            }
        }
        return null;
    }

    public boolean removeToken(String token) {
        Optional<Jws<Claims>> jwsOpt = getJwtToken(token);
        if (!jwsOpt.isPresent()) {
            return true;
        }
        jwsOpt.get().getBody().clear();
        return true;
    }

    public Optional<Jws<Claims>> getJwtToken(String authToken) {
        if (StringUtils.isEmpty(authToken)) {
            return Optional.empty();
        }

        try {
            Jws<Claims> jws = Jwts.parser().setSigningKey(JWT_SECRET)
                    .parseClaimsJws(authToken.replace(TOKEN_PREFIX, StringUtils.EMPTY));
            return Optional.ofNullable(jws);
        } catch (SignatureException e) {
            logger.error("Invalid JWT signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage());
        }

        return Optional.empty();
    }

    public boolean validateJwtToken(String authToken) {
        Optional<Jws<Claims>> jwsOpt = getJwtToken(authToken);
        return jwsOpt.isPresent();
    }

}
