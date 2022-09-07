package com.tutorial.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.HeaderWriterLogoutHandler;
import org.springframework.security.web.header.writers.ClearSiteDataHeaderWriter;
import org.springframework.security.web.header.writers.ClearSiteDataHeaderWriter.Directive;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.cors.CorsConfiguration;

import com.tutorial.config.handle.CustomAccessDeniedHandler;
import com.tutorial.config.handle.CustomLogoutSuccessHandler;
import com.tutorial.config.jwt.CustomAuthEntryPoint;
import com.tutorial.config.jwt.CustomAuthInternalFilter;
import com.tutorial.config.jwt.CustomAuthJWTFilter;
import com.tutorial.config.provide.CustomHttpConfigurer;
import com.tutorial.constant.Role;
import com.tutorial.constant.UrlPathConstant;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private static final RequestMatcher LOGOUT_URLS = new OrRequestMatcher(
            new AntPathRequestMatcher(UrlPathConstant.Authentication.SIGN_OUT));

    private static final ClearSiteDataHeaderWriter.Directive[] SOURCE = { Directive.CACHE, Directive.COOKIES,
            Directive.STORAGE, Directive.EXECUTION_CONTEXTS };

    private final CustomLogoutSuccessHandler customLogoutSuccessHandler;

    private final CustomAccessDeniedHandler customAccessDeniedHandler;

    private final CustomAuthEntryPoint customAuthEntryPoint;

    private final CustomAuthJWTFilter customAuthJWTFilter;

    private final CustomAuthInternalFilter customAuthInternalFilter;

    private final CustomHttpConfigurer customHttpConfigurer;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // @formatter:off
        http.cors().configurationSource(request -> {
            CorsConfiguration cors = new CorsConfiguration();
            cors.setAllowedOriginPatterns(List.of("*"));
            cors.setAllowedMethods(List.of("*"));
            cors.setAllowedHeaders(List.of("*"));
            return cors;
        });
        http.csrf().disable()
            .httpBasic().disable()
            .formLogin().disable();

        http.apply(customHttpConfigurer);
        http
            .addFilterBefore(customAuthJWTFilter, UsernamePasswordAuthenticationFilter.class)
            .addFilterBefore(customAuthInternalFilter, UsernamePasswordAuthenticationFilter.class)
            .authorizeHttpRequests((authorize) -> authorize
                        .antMatchers("/api/authentication/**").permitAll()
                        .antMatchers(HttpMethod.GET, "/api/book").permitAll()
                        .antMatchers("/api/book/**").hasRole(Role.ADMIN.name())
                        .antMatchers("/api/internal/**").hasRole(Role.SYSTEM_MANAGER.name())
                        .anyRequest().authenticated())
            .sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .exceptionHandling((exceptions) -> exceptions
                    .authenticationEntryPoint(customAuthEntryPoint)
                    .accessDeniedHandler(customAccessDeniedHandler)
            );

        http.logout()
            .logoutRequestMatcher(LOGOUT_URLS)
            .deleteCookies("JSESSIONID")
            .invalidateHttpSession(true).logoutSuccessHandler(customLogoutSuccessHandler)
            .addLogoutHandler(new HeaderWriterLogoutHandler(new ClearSiteDataHeaderWriter(SOURCE))).permitAll();
        // @formatter:on

        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
      // @formatter:off
        return (web) -> web.debug(false)
                .ignoring()
                .antMatchers("/css/**", "/js/**", "/img/**", "/lib/**", "/favicon.ico");
     // @formatter:on
    }
}