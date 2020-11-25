package com.epam.esm.sokolov.repository.tag;

import com.epam.esm.sokolov.model.Tag;

import java.util.Optional;

public interface TagRepository {

    Optional<Tag> findTheMostWidelyUsedTag();
}
