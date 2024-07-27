package com.jorshbg.practiceapispring.service;

import com.jorshbg.practiceapispring.model.Post;
import com.jorshbg.practiceapispring.repository.PostRepository;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    public Post getById(Long id) {
        return postRepository.getReferenceById(id);
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
