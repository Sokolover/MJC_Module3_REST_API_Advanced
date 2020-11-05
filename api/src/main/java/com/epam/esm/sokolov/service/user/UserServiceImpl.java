package com.epam.esm.sokolov.service.user;

import com.epam.esm.sokolov.model.User;
import com.epam.esm.sokolov.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }
}
