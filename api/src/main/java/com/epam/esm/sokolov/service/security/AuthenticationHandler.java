package com.epam.esm.sokolov.service.security;

public interface AuthenticationHandler {

    Object getAuthenticationPrincipal();

    Long getCurrentUserId();
}
