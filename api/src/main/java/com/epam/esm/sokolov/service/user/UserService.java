package com.epam.esm.sokolov.service.user;

import com.epam.esm.sokolov.dto.UserDTO;
import com.epam.esm.sokolov.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {

    UserDTO save(UserDTO user);

    List<UserDTO> findAll();
}
