package com.epam.esm.sokolov.security;

import com.epam.esm.sokolov.model.user.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationHandlerImpl implements AuthenticationHandler {

    @Override
    public Object getAuthenticationPrincipal() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getPrincipal();
    }

    @Override
    public Long getCurrentUserId() {
        UserDetailsEntity authenticationPrincipal = (UserDetailsEntity) this.getAuthenticationPrincipal();
        User user = authenticationPrincipal.getUser();
        return user.getId();
    }
}
