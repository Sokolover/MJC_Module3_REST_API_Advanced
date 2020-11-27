package com.epam.esm.sokolov.service.security;

import com.epam.esm.sokolov.model.user.User;
import com.epam.esm.sokolov.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = findUserByUsername(username);
        return new UserDetailsEntity(user);
    }

    private User findUserByUsername(String username) {
        Optional<User> userOptional = userRepository.findUserByUsername(username);
        return userOptional.orElseThrow(
                () -> new UsernameNotFoundException(format("User with username '%s' not found", username))
        );
    }
}
