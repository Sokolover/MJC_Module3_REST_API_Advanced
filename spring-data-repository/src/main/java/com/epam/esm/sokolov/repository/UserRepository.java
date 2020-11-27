package com.epam.esm.sokolov.repository;

import com.epam.esm.sokolov.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query(value = "SELECT * " +
            "FROM user_account " +
            "ORDER BY user_account.id " +
            "LIMIT ?1 " +
            "OFFSET ?2 ", nativeQuery = true)
    List<User> findAll(Long pageSize, Long pageNumber);

    Optional<User> findUserByUsername(String username);
}
