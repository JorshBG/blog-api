package com.jorshbg.practiceapispring.service;

import com.jorshbg.practiceapispring.dto.CommentResponse;
import com.jorshbg.practiceapispring.dto.PagedResponse;
import com.jorshbg.practiceapispring.mapper.CommentMapper;
import com.jorshbg.practiceapispring.model.Comment;
import com.jorshbg.practiceapispring.model.Post;
import com.jorshbg.practiceapispring.model.User;
import com.jorshbg.practiceapispring.repository.CommentRepository;
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
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    private Pageable getPageable(int page){
        return PageRequest.of(page - 1, 5, Sort.Direction.ASC, "id");
    }

    public PagedResponse<CommentResponse> getByAuthor(Long userId, int page, HttpServletRequest request) {
        User user = this.userRepository.findById(userId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        Pageable pageable = getPageable(page);
        Page<Comment> paginated = this.commentRepository.findByAuthor(pageable, user);
        Iterable<CommentResponse> comments = CommentMapper.INSTANCE.toCommentResponse(paginated.getContent());
        return ApiResponseUtility.getPagedResponse(comments, paginated, "/users/".concat(String.valueOf(userId)).concat("/comments"), request);
    }

    public PagedResponse<CommentResponse> getByAuthorAndPost(Long userId, Long postId, int page, HttpServletRequest request) {
        User user = this.userRepository.findById(userId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        Post post = this.postRepository.findById(postId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found"));
        Pageable pageable = getPageable(page);
        Page<Comment> paginated = this.commentRepository.findByAuthorAndPost(pageable, user, post);
        Iterable<CommentResponse> comments = CommentMapper.INSTANCE.toCommentResponse(paginated.getContent());
        String uri = "/users/" + userId + "/posts/" + postId + "/comments";
        return ApiResponseUtility.getPagedResponse(comments, paginated, uri, request);
    }

    public void delete(Long id){
        commentRepository.deleteById(id);
    }

    public Comment save(@NotNull Comment comment){
        return commentRepository.save(comment);
    }

    public Comment update(@NotNull Comment comment){
        return commentRepository.save(comment);
    }

    public Iterable<Comment> getAll(){
        return commentRepository.findAll();
    }

    public Comment getById(Long id){
        return commentRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Comment not found"));
    }

}
