package com.epam.esm.sokolov.repository.user;

import com.epam.esm.sokolov.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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

    Optional<User> findUserByEmail(String email);

    @Transactional
    @Modifying(clearAutomatically = true)
    @org.springframework.data.jpa.repository.Query(value = "INSERT INTO user_account_has_user_role (user_account_id, user_role_id) " +
            "VALUES (:user_id, 2) ", nativeQuery = true)
    void addUserRoles(@Param("user_id") Long id);

    @Transactional
    @Modifying(clearAutomatically = true)
    @org.springframework.data.jpa.repository.Query(value = "UPDATE user_account u " +
            "SET u.password = :password1 " +
            "WHERE u.id = :id ", nativeQuery = true)
    void updateUserPassword(@Param("id") Long id,
                            @Param("password1") String password);


}
