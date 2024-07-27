package com.jorshbg.practiceapispring.repository;

import com.jorshbg.practiceapispring.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
