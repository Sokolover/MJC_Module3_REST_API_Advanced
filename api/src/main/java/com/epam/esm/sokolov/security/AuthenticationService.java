package com.epam.esm.sokolov.security;

import com.epam.esm.sokolov.dto.AuthenticationRequest;

public interface AuthenticationService {

    String generateToken(AuthenticationRequest authenticationRequest);
}
