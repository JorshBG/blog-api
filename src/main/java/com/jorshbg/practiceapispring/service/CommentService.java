package com.jorshbg.practiceapispring.service;

import com.jorshbg.practiceapispring.dto.requests.CommentPostRequest;
import com.jorshbg.practiceapispring.dto.requests.CommentPutRequest;
import com.jorshbg.practiceapispring.dto.responses.CommentResponse;
import com.jorshbg.practiceapispring.dto.responses.PagedResponse;
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
        return this.getPageable(page, 5);
    }

    private Pageable getPageable(int page, int size){
        return PageRequest.of(page - 1, size, Sort.Direction.ASC, "id");
    }

    public PagedResponse<CommentResponse> getByAuthor(Long userId, int page, HttpServletRequest request) {
        User user = this.userRepository.findById(userId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        Pageable pageable = getPageable(page);
        Page<Comment> paginated = this.commentRepository.findByAuthor(pageable, user);
        Iterable<CommentResponse> comments = CommentMapper.INSTANCE.toCommentResponse(paginated.getContent());
        return ApiResponseUtility.getPagedResponse(comments, paginated, "/users/".concat(String.valueOf(userId)).concat("/comments"));
    }

    public PagedResponse<CommentResponse> getByAuthorAndPost(Long userId, Long postId, int page, HttpServletRequest request) {
        User user = this.userRepository.findById(userId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        Post post = this.postRepository.findById(postId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found"));
        Pageable pageable = getPageable(page);
        Page<Comment> paginated = this.commentRepository.findByAuthorAndPost(pageable, user, post);
        Iterable<CommentResponse> comments = CommentMapper.INSTANCE.toCommentResponse(paginated.getContent());
        String uri = "/users/" + userId + "/posts/" + postId + "/comments";
        return ApiResponseUtility.getPagedResponse(comments, paginated, uri);
    }

    public PagedResponse<CommentResponse> getByPost(Long postId, int page, HttpServletRequest request) {
        Post post = this.postRepository.findById(postId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found"));
        Pageable pageable = getPageable(page);
        Page<Comment> paginated = this.commentRepository.findByPost(pageable, post);
        Iterable<CommentResponse> comments = CommentMapper.INSTANCE.toCommentResponse(paginated.getContent());
        String uri = "/posts/" + postId + "/comments";
        return ApiResponseUtility.getPagedResponse(comments, paginated, uri);
    }

    public void delete(Long id){
        this.commentRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Comment not found"));
        commentRepository.deleteById(id);
    }

    public Comment save(@NotNull CommentPostRequest comment){
        Post post = this.postRepository.findById(comment.getPostId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found"));
        User user = this.userRepository.findById(comment.getAuthorId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        Comment created = new Comment();
        created.setAuthor(user);
        created.setPost(post);
        created.setContent(comment.getContent());
        return commentRepository.save(created);
    }

    public Comment update(Long id, @NotNull CommentPutRequest update){
        Comment comment = this.commentRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Comment not found"));
        comment.setContent(update.getContent());
        return commentRepository.save(comment);
    }

    public PagedResponse<CommentResponse> getAll(int page, int size, HttpServletRequest request){
        Pageable pageable = getPageable(page, size);
        Page<Comment> paginated = this.commentRepository.findAll(pageable);
        Iterable<CommentResponse> comments = CommentMapper.INSTANCE.toCommentResponse(paginated.getContent());
        return ApiResponseUtility.getPagedResponse(comments, paginated, "comments");
    }

    public Comment getById(Long id){
        return commentRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Comment not found"));
    }

}
