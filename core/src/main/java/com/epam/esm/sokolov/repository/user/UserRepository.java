package com.epam.esm.sokolov.repository.user;

import com.epam.esm.sokolov.model.user.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    List<User> findAll(Long pageSize, Long pageNumber);

    Long findUserAmount();

    Optional<User> findByUsername(String username);
}
