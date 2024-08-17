package com.jorshbg.practiceapispring.mapper;

import com.jorshbg.practiceapispring.dto.requests.UserPatchRequest;
import com.jorshbg.practiceapispring.dto.requests.UserPostRequest;
import com.jorshbg.practiceapispring.dto.requests.UserPutRequest;
import com.jorshbg.practiceapispring.dto.responses.UserResponse;
import com.jorshbg.practiceapispring.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    void patchUser(UserPatchRequest userPatchRequest, @MappingTarget User user);

    void putUser(UserPutRequest userPutRequest, @MappingTarget User user);

    User createUser(UserPostRequest userPostRequest);

    UserResponse toUserResponse(User user);

    User toUser(UserResponse userResponse);

//    Page<UserResponse> toUserResponses(Page<User> users);
    Iterable<UserResponse> toUserResponses(Iterable<User> users);

}
