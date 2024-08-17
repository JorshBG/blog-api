package com.jorshbg.practiceapispring;

import com.github.javafaker.Faker;
import com.jorshbg.practiceapispring.dto.requests.CommentPostRequest;
import com.jorshbg.practiceapispring.dto.requests.CommentPutRequest;
import com.jorshbg.practiceapispring.model.Comment;
import com.jorshbg.practiceapispring.model.Post;
import com.jorshbg.practiceapispring.model.User;
import com.jorshbg.practiceapispring.repository.CommentRepository;
import com.jorshbg.practiceapispring.repository.PostRepository;
import com.jorshbg.practiceapispring.repository.UserRepository;
import com.jorshbg.practiceapispring.util.FakerBlogData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureWebTestClient
public class CommentTests {

    @Autowired
    private WebTestClient client;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private Faker faker = new Faker();

    @BeforeEach
    void setUp(){
        commentRepository.deleteAll();
        postRepository.deleteAll();
        userRepository.deleteAll();
        jdbcTemplate.execute("TRUNCATE TABLE comments RESTART IDENTITY CASCADE");
        jdbcTemplate.execute("TRUNCATE TABLE posts RESTART IDENTITY CASCADE");
        jdbcTemplate.execute("TRUNCATE TABLE users RESTART IDENTITY CASCADE");
    }

    @Test
    void testFetchAllComments(){
        User user = this.userRepository.save(FakerBlogData.user());
        Post post = this.postRepository.save(FakerBlogData.post(user));
        for (int i = 0; i < 5; i++) {
            this.commentRepository.save(FakerBlogData.comment(user, post));
        }

        client.get().uri("/comments")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .jsonPath("$.data").isNotEmpty()
                .jsonPath("$.links").isNotEmpty()
                .jsonPath("$.metadata").isNotEmpty()
                .jsonPath("$.metadata.totalElements").isEqualTo("5");
    }

    @Test
    void testFetchById(){
        User user = this.userRepository.save(FakerBlogData.user());
        Post post = this.postRepository.save(FakerBlogData.post(user));
        Comment comment = this.commentRepository.save(FakerBlogData.comment(user, post));
        client.get().uri("/comments/" + comment.getId())
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .jsonPath("$.data").isNotEmpty()
                .jsonPath("$.data.content").isEqualTo(comment.getContent())
                .jsonPath("$.data.author").isEqualTo(comment.getAuthor().getUsername())
                .jsonPath("$.links").isNotEmpty();
    }

    @Test
    void testCreateComment(){
        User user = this.userRepository.save(FakerBlogData.user());
        Post post = this.postRepository.save(FakerBlogData.post(user));
        CommentPostRequest comment = new CommentPostRequest();
        comment.setContent(faker.lorem().paragraph());
        comment.setAuthorId(user.getId());
        comment.setPostId(post.getId());
        client.post().uri("/comments")
                .bodyValue(comment)
                .exchange()
                .expectStatus()
                .isCreated()
                .expectBody()
                .jsonPath("$.data").isNotEmpty()
                .jsonPath("$.data.content").isEqualTo(comment.getContent())
                .jsonPath("$.links").isNotEmpty();
    }

    @Test
    void testDeleteComment(){
        User user = this.userRepository.save(FakerBlogData.user());
        Post post = this.postRepository.save(FakerBlogData.post(user));
        Comment comment = this.commentRepository.save(FakerBlogData.comment(user, post));
        client.delete().uri("/comments/" + comment.getId())
                .exchange()
                .expectStatus()
                .isNoContent()
                .expectBody()
                .isEmpty();
    }

    @Test
    void testUpdateComment(){
        User user = this.userRepository.save(FakerBlogData.user());
        Post post = this.postRepository.save(FakerBlogData.post(user));
        Comment comment = this.commentRepository.save(FakerBlogData.comment(user, post));
        CommentPutRequest update = new CommentPutRequest();
        update.setContent(faker.lorem().paragraph());
        client.put().uri("/comments/" + comment.getId())
                .bodyValue(update)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .jsonPath("$.data").isNotEmpty()
                .jsonPath("$.links").isNotEmpty();
    }

}
