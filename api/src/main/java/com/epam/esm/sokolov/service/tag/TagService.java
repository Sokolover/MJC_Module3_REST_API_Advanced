package com.epam.esm.sokolov.service.tag;

import com.epam.esm.sokolov.dto.TagDTO;
import org.springframework.stereotype.Service;

@Service
public interface TagService {

    TagDTO findTheMostWidelyUsedTag();
}
