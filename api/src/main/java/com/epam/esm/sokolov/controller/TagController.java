package com.epam.esm.sokolov.controller;

import com.epam.esm.sokolov.dto.TagDTO;
import com.epam.esm.sokolov.exception.CertificateAppException;
import com.epam.esm.sokolov.service.tag.TagService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static com.epam.esm.sokolov.constants.CommonAppConstants.MOST_WIDELY_USED_SEARCH_PARAM;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/tags")
@Api(value = "TagControllerApi", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class TagController {

    private final TagService tagService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public TagDTO findTheMostWidelyUsedTag(String searchFor) {
        if (MOST_WIDELY_USED_SEARCH_PARAM.equals(searchFor)) {//todo choose endpoint or request param
            TagDTO tagDTO = tagService.findTheMostWidelyUsedTag();
            tagDTO.add(linkTo(methodOn(TagController.class)
                    .findTheMostWidelyUsedTag(searchFor)).withSelfRel());
            return tagDTO;
        } else {
            throw new CertificateAppException("Resource not found", HttpStatus.BAD_REQUEST, this.getClass());
        }
    }
}
