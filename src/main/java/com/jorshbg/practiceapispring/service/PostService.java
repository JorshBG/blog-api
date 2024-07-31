package com.jorshbg.practiceapispring.service;

import com.jorshbg.practiceapispring.dto.PagedResponse;
import com.jorshbg.practiceapispring.dto.PostResponse;
import com.jorshbg.practiceapispring.mapper.PostMapper;
import com.jorshbg.practiceapispring.model.Post;
import com.jorshbg.practiceapispring.model.User;
import com.jorshbg.practiceapispring.repository.PostRepository;
import com.jorshbg.practiceapispring.repository.UserRepository;
import com.jorshbg.practiceapispring.util.ApiResponseUtility;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    public PagedResponse<PostResponse> getAllPosts(int page, HttpServletRequest request) {
        Pageable pageable = PageRequest.of(page - 1, 10, Sort.Direction.ASC, "id");
        Page<Post> paginated = postRepository.findAll(pageable);
        Iterable<PostResponse> posts = PostMapper.INSTANCE.toPostResponse(paginated.getContent());

        return ApiResponseUtility.getPagedResponse(posts, paginated, "posts", request);
    }

    public PostResponse getById(Long id) {
        Post post =  postRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found")
        );
        return PostMapper.INSTANCE.toPostResponse(post);
    }

    public PagedResponse<PostResponse> getByAuthor(Long userId, Integer page, HttpServletRequest request) {
        User user = this.userRepository.findById(userId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        Pageable pageable = PageRequest.of(page - 1, 10, Sort.Direction.ASC, "id");
        Page<Post> paginated = this.postRepository.findByAuthor(user, pageable);
        Iterable<PostResponse> posts = PostMapper.INSTANCE.toPostResponse(paginated.getContent());
        return ApiResponseUtility.getPagedResponse(posts, paginated, "/users/".concat(String.valueOf(userId)).concat("/posts"), request);
    }

    public void deleteById(Long id){
        postRepository.deleteById(id);
    }

    public Post save(@NotNull Post post){
        return postRepository.save(post);
    }

    public Post update(@NotNull Post post){
        return postRepository.save(post);
    }

}
