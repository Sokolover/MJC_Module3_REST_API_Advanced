package com.epam.esm.sokolov.repository;

import com.epam.esm.sokolov.model.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository {

    User save(User order);

    List<User> findAll();
}
