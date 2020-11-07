package com.epam.esm.sokolov.service.tag;

import com.epam.esm.sokolov.converter.TagConverter;
import com.epam.esm.sokolov.dto.TagDTO;
import com.epam.esm.sokolov.model.Tag;
import com.epam.esm.sokolov.repository.TagRepository;
import com.epam.esm.sokolov.service.ServiceException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class TagServiceImpl implements TagService {

    private TagRepository tagRepository;
    private TagConverter tagConverter;

    public TagServiceImpl(TagRepository tagRepository, TagConverter tagConverter) {
        this.tagRepository = tagRepository;
        this.tagConverter = tagConverter;
    }

    @Override
    public TagDTO findTheMostWidelyUsedTag() {
        Tag tag = tagRepository.findTheMostWidelyUsedTag().<ServiceException>orElseThrow(() -> {
            throw new ServiceException("Requested resource not found", HttpStatus.NOT_FOUND, this.getClass());
        });//todo make throwing ServiceException in similar cases (!) read about <ServiceException>orElseThrow
        return tagConverter.convert(tag);
    }
}
