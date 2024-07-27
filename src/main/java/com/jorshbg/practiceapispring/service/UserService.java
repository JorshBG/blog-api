package com.jorshbg.practiceapispring.service;

import com.jorshbg.practiceapispring.model.User;
import com.jorshbg.practiceapispring.repository.UserRepository;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder encoder;

    public User save(@NotNull User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public User getUserById(Long id){
        return userRepository.getReferenceById(id);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void deleteUserById(Long id){
        userRepository.deleteById(id);
    }

    public User updateUser(@NotNull User user){
        user.setPassword(encoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

}
