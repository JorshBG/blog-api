package com.jorshbg.practiceapispring.util;

import com.jorshbg.practiceapispring.dto.*;
import com.jorshbg.practiceapispring.mapper.CommentMapper;
import com.jorshbg.practiceapispring.mapper.PostMapper;
import com.jorshbg.practiceapispring.mapper.UserMapper;
import com.jorshbg.practiceapispring.model.Comment;
import com.jorshbg.practiceapispring.model.Post;
import com.jorshbg.practiceapispring.model.User;

public class MapperUtil {

    public static UserResponse toResponse(User user) {
        return UserMapper.INSTANCE.toUserResponse(user);
    }

    public static CommentResponse toResponse(Comment comment){
        return CommentMapper.INSTANCE.toCommentResponse(comment);
    }

    public static PostResponse toResponse(Post post){
        return PostMapper.INSTANCE.toPostResponse(post);
    }

    public static User toModel(UserResponse userResponse) {
        return UserMapper.INSTANCE.toUser(userResponse);
    }

    public static void convert(UserPutRequest request, User user){
        UserMapper.INSTANCE.putUser(request, user);
    }

    public static void convert(UserPatchRequest request, User user){
        UserMapper.INSTANCE.patchUser(request, user);
    }



}
