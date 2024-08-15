package com.jorshbg.practiceapispring.repository;

import com.jorshbg.practiceapispring.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ITagRepository extends JpaRepository<Tag, Integer> {
}
