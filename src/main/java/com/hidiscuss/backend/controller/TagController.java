package com.hidiscuss.backend.controller;

import com.hidiscuss.backend.controller.dto.TagResponseDto;
import com.hidiscuss.backend.entity.Tag;
import com.hidiscuss.backend.service.TagService;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/tag")
@AllArgsConstructor
public class TagController {

    private final TagService tagService;

    @ApiOperation(value = "존재하는 태그를 반환")
    @GetMapping("")
    public List<TagResponseDto> findAll() {
        List<Tag> tags = tagService.findAll();
        return tags.stream().map(TagResponseDto::fromEntity).collect(Collectors.toList());
    }
}
