package com.epam.esm.sokolov.repository;

import com.epam.esm.sokolov.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Long> {
}
