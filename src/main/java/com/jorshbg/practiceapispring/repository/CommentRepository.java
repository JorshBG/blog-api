package com.jorshbg.practiceapispring.repository;

import com.jorshbg.practiceapispring.model.Comment;
import com.jorshbg.practiceapispring.model.Post;
import com.jorshbg.practiceapispring.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends CrudRepository<Comment, Long> {

    Page<Comment> findByAuthor(Pageable pageable, User author);

    Page<Comment> findAll(Pageable pageable);

    Page<Comment> findByPost(Pageable pageable, Post post);

    Page<Comment> findByAuthorAndPost(Pageable pageable, User author, Post post);

}
