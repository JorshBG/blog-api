package com.jorshbg.practiceapispring.mapper;

import com.jorshbg.practiceapispring.dto.PostPatchRequest;
import com.jorshbg.practiceapispring.dto.PostPutRequest;
import com.jorshbg.practiceapispring.dto.PostResponse;
import com.jorshbg.practiceapispring.model.Post;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PostMapper {

    PostMapper INSTANCE = Mappers.getMapper(PostMapper.class);

    PostResponse toPostResponse(Post post);

    Iterable<PostResponse> toPostResponse(Iterable<Post> posts);

    void putPost(PostPutRequest update, @MappingTarget Post post);

    void patchPost(PostPatchRequest update, @MappingTarget Post post);

}
