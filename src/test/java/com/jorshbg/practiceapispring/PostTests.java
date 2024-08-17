package com.jorshbg.practiceapispring;

import com.github.javafaker.Faker;
import com.jorshbg.practiceapispring.dto.requests.PostPostRequest;
import com.jorshbg.practiceapispring.model.Post;
import com.jorshbg.practiceapispring.model.User;
import com.jorshbg.practiceapispring.repository.CommentRepository;
import com.jorshbg.practiceapispring.repository.PostRepository;
import com.jorshbg.practiceapispring.repository.UserRepository;
import com.jorshbg.practiceapispring.util.FakerBlogData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.Random;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureWebTestClient
public class PostTests {

    @Autowired
    private WebTestClient client;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private Faker faker = new Faker();

    @BeforeEach
    void setUp() {
        commentRepository.deleteAll();
        postRepository.deleteAll();
        userRepository.deleteAll();
        jdbcTemplate.execute("TRUNCATE TABLE comments RESTART IDENTITY CASCADE");
        jdbcTemplate.execute("TRUNCATE TABLE posts RESTART IDENTITY CASCADE");
        jdbcTemplate.execute("TRUNCATE TABLE users RESTART IDENTITY CASCADE");

        for (int i = 0; i < 4; i++) {
            User user = userRepository.save(FakerBlogData.user());
            for (int j = 0; j < 3; j++) {
                Post post = postRepository.save(FakerBlogData.post(user));
                int countComments = (new Random()).nextInt(1, 6);
                for (int k = 0; k < countComments; k++) {
                    commentRepository.save(FakerBlogData.comment(user, post));
                }
            }
        }

    }

    @Test
    void testFetchAllPosts() {
        client.get().uri("/posts")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .jsonPath("$.data").isNotEmpty()
                .jsonPath("$.data[0].title").isNotEmpty()
                .jsonPath("$.data[0].author").isNotEmpty()
                .jsonPath("$.links").isNotEmpty()
                .jsonPath("$.metadata").isNotEmpty()
                .jsonPath("$.metadata.totalElements").isEqualTo(12);
    }

    @Test
    void testFetchPostById() {
        client.get().uri("/posts/1")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .jsonPath("$.data.title").isNotEmpty()
                .jsonPath("$.links").isNotEmpty()
                .jsonPath("$.links.self").isNotEmpty()
                .jsonPath("$.links.self").value(val -> Assertions.assertEquals("/api/v1/posts/1", val));
    }

    @Test
    void testCreatePost(){
        User user = userRepository.save(FakerBlogData.user());
        PostPostRequest post = new PostPostRequest();
        post.setAuthorId(user.getId());
        post.setTitle(faker.name().title());
        post.setContent(faker.lorem().paragraph());
        client.post().uri("/posts")
                .bodyValue(post)
                .exchange()
                .expectStatus()
                .isCreated()
                .expectBody()
                .jsonPath("$.data.title").isEqualTo(post.getTitle())
                .jsonPath("$.links").isNotEmpty();
    }

    @Test
    void testUpdatePost(){
        Post post = this.postRepository.save(FakerBlogData.post(userRepository.save(FakerBlogData.user())));
        post.setTitle(faker.name().title());
        post.setContent(faker.lorem().paragraph());
        client.put().uri("/posts/" + post.getId())
                .bodyValue(post)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .jsonPath("$.data.title").isEqualTo(post.getTitle())
                .jsonPath("$.links").isNotEmpty();
    }

    @Test
    void testDeletePost(){
        Post post = this.postRepository.save(FakerBlogData.post(userRepository.save(FakerBlogData.user())));
        client.delete().uri("/posts/" + post.getId())
                .exchange()
                .expectStatus()
                .isNoContent()
                .expectBody()
                .isEmpty();
    }

    @Test
    void testPartialUpdate(){
        Post post = this.postRepository.save(FakerBlogData.post(userRepository.save(FakerBlogData.user())));
        post.setTitle(faker.lorem().sentence());
        client.patch().uri("/posts/" + post.getId())
                .bodyValue(post)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .jsonPath("$.data.title").isEqualTo(post.getTitle())
                .jsonPath("$.links").isNotEmpty();
    }

    @Test
    void testFetchCommentsByPostId(){
        Post post = this.postRepository.save(FakerBlogData.post(userRepository.save(FakerBlogData.user())));
        for (int i = 0; i < 10; i++) {
            this.commentRepository.save(FakerBlogData.comment(userRepository.save(FakerBlogData.user()), post));
        }
        client.get().uri("/posts/" + post.getId() + "/comments")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .jsonPath("$.data").isNotEmpty()
                .jsonPath("$.metadata.totalElements").isEqualTo("10")
                .jsonPath("$.links").isNotEmpty();
    }

}
