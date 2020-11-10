package com.epam.esm.sokolov.repository;

import com.epam.esm.sokolov.model.Tag;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TagRepository {

    Optional<Tag> findTheMostWidelyUsedTag();
}
