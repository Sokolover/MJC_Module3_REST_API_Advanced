package com.epam.esm.sokolov.repository.user;

import com.epam.esm.sokolov.model.user.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    List<User> findAll(Long pageSize, Long pageNumber);

    Optional<User> findUserByUsername(String username);

    Optional<User> findUserByEmail(String email);

    User save(User user);

    Optional<User> findById(Long id);

    Long count();
}