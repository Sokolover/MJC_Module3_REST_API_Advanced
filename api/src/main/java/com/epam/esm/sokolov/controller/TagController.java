package com.epam.esm.sokolov.controller;

import com.epam.esm.sokolov.dto.TagDTO;
import com.epam.esm.sokolov.service.tag.TagService;
import io.swagger.annotations.Api;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/tags")
@Api(value = "TagControllerApi", produces = MediaType.APPLICATION_JSON_VALUE)
public class TagController {

    private TagService tagService;

    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping("/the-most-widely-used-tag")//todo 3) maybe think about shorter mapping
    @ResponseStatus(HttpStatus.OK)
    public TagDTO findTheMostWidelyUsedTag() {
        return tagService.findTheMostWidelyUsedTag();
    }
}
