package com.jorshbg.practiceapispring.repository;

import com.jorshbg.practiceapispring.model.Post;
import com.jorshbg.practiceapispring.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends CrudRepository<Post, Long> {

    Page<Post> findAll(Pageable pageable);

    Page<Post> findByTitleContaining(String title, Pageable pageable);

    Page<Post> findByAuthor(User author, Pageable pageable);

}
