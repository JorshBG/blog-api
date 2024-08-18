package com.jorshbg.practiceapispring.service;

import com.jorshbg.practiceapispring.dto.requests.UserPatchRequest;
import com.jorshbg.practiceapispring.dto.requests.UserPostRequest;
import com.jorshbg.practiceapispring.dto.requests.UserPutRequest;
import com.jorshbg.practiceapispring.dto.responses.PagedResponse;
import com.jorshbg.practiceapispring.dto.responses.UserResponse;
import com.jorshbg.practiceapispring.mapper.UserMapper;
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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PasswordEncoder encoder;

    public UserResponse create(@NotNull UserPostRequest userPost) {
        User user = UserMapper.INSTANCE.createUser(userPost);
        user.setPassword(encoder.encode(user.getPassword()));
        return UserMapper.INSTANCE.toUserResponse(userRepository.save(user));
    }

    public UserResponse readById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found")
        );
        return UserMapper.INSTANCE.toUserResponse(user);
    }

    public PagedResponse<UserResponse> readAll(int page, int size, HttpServletRequest request) {
        Pageable pageable = PageRequest.of(page - 1, size,Sort.Direction.ASC,
                        "id");
        Page<User> paginated = userRepository.findAll(pageable);
        Iterable<UserResponse> users = UserMapper.INSTANCE.toUserResponses(paginated.getContent());
        return new ApiResponseUtility().getPagedResponse(users, paginated, "users");
    }

    public UserResponse updatePut(Long id, @NotNull UserPutRequest update) {
        User user = userRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found")
        );
        UserMapper.INSTANCE.putUser(update, user);
        user.setPassword(encoder.encode(user.getPassword()));
        return UserMapper.INSTANCE.toUserResponse(userRepository.save(user));
    }

    public UserResponse updatePatch(Long id, @NotNull UserPatchRequest update) {
        User user = userRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found")
        );
        UserMapper.INSTANCE.patchUser(update, user);
        if(update.getPassword() != null){
            user.setPassword(encoder.encode(user.getPassword()));
        }
        return UserMapper.INSTANCE.toUserResponse(userRepository.save(user));
    }

    public void deleteById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found")
        );
        userRepository.deleteById(user.getId());
    }


}
