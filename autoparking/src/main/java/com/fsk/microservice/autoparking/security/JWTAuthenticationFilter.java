package com.fsk.microservice.autoparking.security;

import com.fsk.microservice.autoparking.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.HttpHeaders;
import java.io.IOException;
import java.util.Optional;

@Slf4j
public class JWTAuthenticationFilter extends GenericFilterBean {
    private static final String BEARER = "Bearer";

    private UserService userService;

    public JWTAuthenticationFilter(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        String headerValue = ((HttpServletRequest) request).getHeader(HttpHeaders.AUTHORIZATION);
        Optional<String> token = getBearerToken(headerValue);
        log.info(userService.toString());
        if (token.isPresent()) {
            userService.loadUserByJwtToken(token.get()).ifPresent(userDetails -> {
                SecurityContextHolder.getContext().setAuthentication(
                        new PreAuthenticatedAuthenticationToken(userDetails, "", userDetails.getAuthorities()));
            });
        }
        chain.doFilter(request, response);
    }

    private Optional<String> getBearerToken(String headerVal) {

        if (headerVal != null && headerVal.startsWith(BEARER)) {
            String token = headerVal.replace(BEARER, "").trim();
            log.info("header values = {}", token);
            return Optional.of(token);
        }
        return Optional.empty();
    }
}
