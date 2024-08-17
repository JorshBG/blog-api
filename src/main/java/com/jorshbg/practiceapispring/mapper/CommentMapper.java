package com.jorshbg.practiceapispring.mapper;

import com.jorshbg.practiceapispring.dto.responses.CommentResponse;
import com.jorshbg.practiceapispring.model.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CommentMapper {

    CommentMapper INSTANCE = Mappers.getMapper(CommentMapper.class);

    Iterable<CommentResponse> toCommentResponse(Iterable<Comment> comments);

    CommentResponse toCommentResponse(Comment comment);
}
