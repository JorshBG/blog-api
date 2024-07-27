package com.jorshbg.practiceapispring.service;

import com.jorshbg.practiceapispring.model.Comment;
import com.jorshbg.practiceapispring.repository.CommentRepository;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    @Autowired
    private CommentRepository repository;

    public void delete(Long id){
        repository.deleteById(id);
    }

    public Comment save(@NotNull Comment comment){
        return repository.save(comment);
    }

    public Comment update(@NotNull Comment comment){
        return repository.save(comment);
    }

    public List<Comment> getAll(){
        return repository.findAll();
    }

    public Comment getById(Long id){
        return repository.getReferenceById(id);
    }

}
