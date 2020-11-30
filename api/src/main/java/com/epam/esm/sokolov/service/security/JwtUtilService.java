package com.epam.esm.sokolov.service.security;

import org.springframework.security.core.userdetails.UserDetails;

public interface JwtUtilService {

    boolean isTokenValid(String token, UserDetails userDetails);

    String generateToken(UserDetails userDetails);

    String getUsernameFromToken(String token);
}
