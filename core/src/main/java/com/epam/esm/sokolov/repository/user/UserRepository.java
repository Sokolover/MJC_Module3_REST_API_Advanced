package com.epam.esm.sokolov.repository.user;

import com.epam.esm.sokolov.model.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository {

    List<User> findAll(Long pageSize, Long pageNumber);

    Long findUserAmount();
}
