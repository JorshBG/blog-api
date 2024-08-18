package com.jorshbg.practiceapispring.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IBlogDefaultRepository<E, I> extends JpaRepository<E, I> {

    Page<E> findByActive(Boolean active, Pageable pageable);

    E findByIdAndActive(I id, Boolean active);

}
