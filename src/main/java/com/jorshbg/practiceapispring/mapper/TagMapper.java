package com.jorshbg.practiceapispring.mapper;

import com.jorshbg.practiceapispring.dto.responses.TagResponse;
import com.jorshbg.practiceapispring.model.Tag;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TagMapper {

    TagMapper INSTANCE = Mappers.getMapper(TagMapper.class);

    TagResponse toResponse(Tag tag);

    Iterable<TagResponse> toResponses(Iterable<Tag> tags);

    void update(Tag update, @MappingTarget Tag previous);

}