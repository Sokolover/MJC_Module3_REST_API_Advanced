package com.epam.esm.sokolov.service.user;

import com.epam.esm.sokolov.model.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    User save(User user);
}
