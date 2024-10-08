package com.jorshbg.practiceapispring.repository;

import com.jorshbg.practiceapispring.model.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ITagRepository extends IBlogDefaultRepository<Tag, Long>{

    Optional<Tag> findByName(String name);

}
