package com.epam.esm.sokolov.repository.role;

import com.epam.esm.sokolov.model.user.Role;

import java.util.Optional;

/**
 * @author Sokolov_SA
 * @created 07.11.2021
 */
public interface RoleRepository {

    Optional<Role> findByName(String name);
}
