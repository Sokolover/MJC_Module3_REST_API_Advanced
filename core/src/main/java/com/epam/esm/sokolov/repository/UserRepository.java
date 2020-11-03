package com.epam.esm.sokolov.repository;

import com.epam.esm.sokolov.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
