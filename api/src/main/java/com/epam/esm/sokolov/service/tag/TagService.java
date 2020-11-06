package com.epam.esm.sokolov.service.tag;

import com.epam.esm.sokolov.dto.TagDTO;
import com.epam.esm.sokolov.model.Tag;
import org.springframework.stereotype.Service;

@Service
public interface TagService {

    TagDTO findTheMostWidelyUsedTag();
}
