package com.epam.esm.sokolov.service.user;

import com.epam.esm.sokolov.dto.UserDTO;
import com.epam.esm.sokolov.model.user.User;

import java.util.List;

public interface UserService {

    List<UserDTO> findAll(Long pageSize, Long pageNumber);

    Long findUserAmount();

    User findById(Long id);

    UserDTO save(UserDTO userDTO);
}
