package com.epam.esm.sokolov.filter;

import com.epam.esm.sokolov.exception.ErrorResponse;
import com.epam.esm.sokolov.exception.FilterException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.springframework.core.Ordered.HIGHEST_PRECEDENCE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Component
@Order(value = HIGHEST_PRECEDENCE)
public class ExceptionHandlerFilter extends OncePerRequestFilter {

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (FilterException e) {
            ErrorResponse errorResponse = new ErrorResponse(e);
            setResponseProperties(response, e);
            response.getWriter().write(convertObjectToJson(errorResponse));
        }
    }

    private void setResponseProperties(HttpServletResponse response, FilterException e) {
        response.setStatus(e.getHttpStatus().value());
        response.setContentType(APPLICATION_JSON_VALUE);

        response.setCharacterEncoding(UTF_8.name());
    }

    private String convertObjectToJson(Object object) throws JsonProcessingException {
        if (object == null) {
            return "Unable to convert object to JSON";
        }
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(object);
    }
}
