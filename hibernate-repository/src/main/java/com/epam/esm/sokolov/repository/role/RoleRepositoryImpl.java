package com.epam.esm.sokolov.repository.role;

import com.epam.esm.sokolov.exception.RepositoryException;
import com.epam.esm.sokolov.model.user.Role;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManagerFactory;
import java.util.Optional;

import static java.lang.String.format;

/**
 * @author Sokolov_SA
 * @created 07.11.2021
 */
@Repository
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class RoleRepositoryImpl implements RoleRepository {

    private final EntityManagerFactory entityManagerFactory;

    @Override
    public Optional<Role> findByName(String name) {
        try (Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession()) {
            return Optional.ofNullable(session.createNativeQuery(
                    "select * from user_role where name = :roleName", Role.class)
                    .setParameter("roleName", name)
                    .getSingleResult());
        } catch (Exception e) {
            String message = format("Resource not found (name = %s)", name);
            throw new RepositoryException(message, HttpStatus.NOT_FOUND, this.getClass());
        }
    }
}
