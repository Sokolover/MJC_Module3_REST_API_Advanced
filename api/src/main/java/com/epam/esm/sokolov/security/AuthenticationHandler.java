package com.epam.esm.sokolov.security;

public interface AuthenticationHandler {

    Object getAuthenticationPrincipal();

    Long getCurrentUserId();
}
