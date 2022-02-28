package com.epam.esm.sokolov.service.user;

import com.epam.esm.sokolov.dto.UserInDTO;
import com.epam.esm.sokolov.dto.UserOutDTO;
import com.epam.esm.sokolov.model.user.User;

import java.util.List;

public interface UserService {

    List<UserOutDTO> findAll(Long pageSize, Long pageNumber);

    Long findUserAmount();

    User findById(Long id);

    UserOutDTO signUp(UserInDTO userInDTO);
}
