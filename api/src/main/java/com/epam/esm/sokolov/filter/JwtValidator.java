package com.epam.esm.sokolov.filter;

import com.epam.esm.sokolov.exception.FilterException;
import com.epam.esm.sokolov.service.security.JwtUtilService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.epam.esm.sokolov.constants.CommonAppConstants.*;
import static org.springframework.core.Ordered.HIGHEST_PRECEDENCE;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Component
@Order(value = HIGHEST_PRECEDENCE + 1)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class JwtValidator extends OncePerRequestFilter {

    private final JwtUtilService jwtUtilService;

    private void validateJwt(HttpServletRequest request) {

        final String requestTokenHeader = request.getHeader(AUTHORIZATION);

        if (requestTokenHeader != null && requestTokenHeader.startsWith(BEARER)) {
            String jwtToken = requestTokenHeader.substring(BEARER_HEADER_OFFSET);
            try {
                String username = jwtUtilService.getUsernameFromToken(jwtToken);
                request.setAttribute(USERNAME_REQUEST_ATTRIBUTE, username);
                request.setAttribute(JWT_REQUEST_ATTRIBUTE, jwtToken);
            } catch (MalformedJwtException e) {
                throw new FilterException(e.getMessage(), HttpStatus.UNAUTHORIZED);
            } catch (SignatureException e) {
                throw new FilterException("Calculating a signature or verifying an existing signature of a JWT failed", HttpStatus.UNAUTHORIZED);
            } catch (IllegalArgumentException e) {
                throw new FilterException("Unable to get JWT Token", HttpStatus.UNAUTHORIZED);
            } catch (ExpiredJwtException e) {
                throw new FilterException("JWT Token has expired", HttpStatus.UNAUTHORIZED);
            }
        }
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        validateJwt(request);
        filterChain.doFilter(request, response);
    }
}
