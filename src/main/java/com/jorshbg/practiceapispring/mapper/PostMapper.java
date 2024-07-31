package com.jorshbg.practiceapispring.mapper;

import com.jorshbg.practiceapispring.dto.PostResponse;
import com.jorshbg.practiceapispring.model.Post;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PostMapper {

    PostMapper INSTANCE = Mappers.getMapper(PostMapper.class);

    PostResponse toPostResponse(Post post);

    Iterable<PostResponse> toPostResponse(Iterable<Post> posts);

}
