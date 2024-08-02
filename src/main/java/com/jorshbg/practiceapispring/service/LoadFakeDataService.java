package com.jorshbg.practiceapispring.service;

import com.github.javafaker.Faker;
import com.jorshbg.practiceapispring.model.Comment;
import com.jorshbg.practiceapispring.model.Post;
import com.jorshbg.practiceapispring.model.User;
import com.jorshbg.practiceapispring.repository.CommentRepository;
import com.jorshbg.practiceapispring.repository.PostRepository;
import com.jorshbg.practiceapispring.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

//@Service
public class LoadFakeDataService {

//    @Autowired
    private PasswordEncoder encoder;

    private Faker faker = new Faker();
    private Random random = new Random();

//    @Autowired
    private UserRepository userRepository;

//    @Autowired
    private PostRepository postRepository;

//    @Autowired
    private CommentRepository commentRepository;

//    @Autowired
    private JdbcTemplate jdbcTemplate;

//    @PostConstruct
//    @Transactional
    public void init(){
        userRepository.deleteAll();
        postRepository.deleteAll();
        commentRepository.deleteAll();
        jdbcTemplate.execute("TRUNCATE TABLE comments RESTART IDENTITY CASCADE");
        jdbcTemplate.execute("TRUNCATE TABLE posts RESTART IDENTITY CASCADE");
        jdbcTemplate.execute("TRUNCATE TABLE users RESTART IDENTITY CASCADE");

        List<User> users = new ArrayList<>();
        List<Post> posts = new ArrayList<>();

        for (int i = 0; i < 20; i++) {
            User user = new User();
            user.setUsername(faker.name().username());
            user.setFirstName(faker.name().firstName());
            user.setLastName(faker.name().lastName());
            user.setEmail(faker.internet().safeEmailAddress());
            user.setPassword(encoder.encode(faker.internet().password()));
            user.setBiography(faker.lorem().sentence());
            user.setBirthday(LocalDate.of(random.nextInt(1980, 2010), random.nextInt(1, 12), random.nextInt(1, 28)));
            user.setPhone(faker.phoneNumber().cellPhone());
            user.setRememberToken(faker.lorem().sentence());
            users.add(userRepository.save(user));
        }

        for (int i = 0; i < 200; i++) {
            Post post = new Post();
            post.setContent(faker.lorem().paragraph(40));
            post.setTitle(faker.lorem().sentence(5));
            post.setAuthor(users.get(random.nextInt(users.size())));
            posts.add(postRepository.save(post));
        }

        for (int i = 0; i < 150; i++) {
            Comment comment = new Comment();
            comment.setContent(faker.lorem().sentence(5));
            comment.setPost(posts.get(random.nextInt(posts.size())));
            comment.setAuthor(users.get(random.nextInt(users.size())));
            commentRepository.save(comment);
        }
    }

}
