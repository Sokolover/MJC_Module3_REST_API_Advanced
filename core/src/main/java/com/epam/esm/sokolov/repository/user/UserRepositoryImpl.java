package com.epam.esm.sokolov.repository.user;

import com.epam.esm.sokolov.model.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class UserRepositoryImpl implements UserRepository {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public User save(User user) {
        entityManager.persist(user);
        return user;
    }

    @Override
    public List<User> findAll() {
        Query query = entityManager.createNativeQuery("SELECT * FROM user_account", User.class);
        return query.getResultList();
    }
}
