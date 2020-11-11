package com.epam.esm.sokolov.service.user;

import com.epam.esm.sokolov.dto.UserDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {

    List<UserDTO> findAll(Long pageSize, Long pageNumber);

    Long findUserAmount();

}
