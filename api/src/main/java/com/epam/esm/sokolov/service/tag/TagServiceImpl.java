package com.epam.esm.sokolov.service.tag;

import com.epam.esm.sokolov.converter.TagConverter;
import com.epam.esm.sokolov.dto.TagDTO;
import com.epam.esm.sokolov.exception.ServiceException;
import com.epam.esm.sokolov.model.Tag;
import com.epam.esm.sokolov.repository.tag.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;
    private final TagConverter tagConverter;

    @Override
    public TagDTO findTheMostWidelyUsedTag() {
        Tag tag = tagRepository.findTheMostWidelyUsedTag().<ServiceException>orElseThrow(() -> {
            throw new ServiceException("Resource not found", HttpStatus.NOT_FOUND, this.getClass());
        });
        return tagConverter.convert(tag);
    }
}
