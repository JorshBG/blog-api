package com.jorshbg.practiceapispring.repository;

import com.jorshbg.practiceapispring.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
}
