package com.jorshbg.practiceapispring;

import com.github.javafaker.Faker;
import com.jorshbg.practiceapispring.model.Post;
import com.jorshbg.practiceapispring.model.User;
import com.jorshbg.practiceapispring.repository.CommentRepository;
import com.jorshbg.practiceapispring.repository.PostRepository;
import com.jorshbg.practiceapispring.repository.UserRepository;
import com.jorshbg.practiceapispring.util.FakerBlogData;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import java.util.Random;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
public class UserTests {

    @Autowired
    private WebTestClient client;

    @Autowired
    private UserRepository repository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentRepository commentRepository;

    private Faker faker = new Faker();
    private Random random = new Random();

    @BeforeAll
    static void setUp(){

    }

    @Test
    void testListAllUsers() {
        for (int i = 0; i < 5; i++) {
            this.repository.save(FakerBlogData.user());
        }

        client.get().uri("/users")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .jsonPath("$.data.length()").isEqualTo(10)
                .jsonPath("$.data[0].id").isNotEmpty()
                .jsonPath("$.links").isNotEmpty()
                .jsonPath("$.metadata").isNotEmpty();
    }

    @Test
    void testCreateUser() {
        User user = FakerBlogData.user();
        user.setFirstName("John");
        client.post().uri("/users")
                .bodyValue(user)
                .exchange()
                .expectStatus()
                .isCreated()
                .expectBody()
                .jsonPath("$.data").isNotEmpty()
                .jsonPath("$.data.firstName").isEqualTo("John")
                .jsonPath("$.links").isNotEmpty();//1-976-930-9260
    }

    @Test
    void testDeleteUser() {
        User user = FakerBlogData.user();
        User created = repository.save(user);

        client.delete().uri("/users/" + created.getId())
                .headers(headers -> headers.setBasicAuth(user.getUsername(), user.getPassword()))
                .exchange()
                .expectStatus()
                .isNoContent()
                .expectBody()
                .isEmpty();
    }

    @Test
    void testGetOneUser() {
        User created = repository.save(FakerBlogData.user());
        client.get().uri("/users/" + created.getId())
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .jsonPath("$.data").isNotEmpty()
                .jsonPath("$.links").isNotEmpty();
    }

    @Test
    void testTotalUpdateUser() {
        // New user
        User updated = repository.save(FakerBlogData.user());

        // required fields
        var name = faker.name().firstName();
        var email = faker.internet().emailAddress();
        var username = faker.name().username();

        updated.setFirstName(name);
        updated.setLastName("Hellsing");
        updated.setEmail(email);
        updated.setPassword("Hellsing");
        updated.setUsername(username);

        // Update
        client.put().uri("/users/" + updated.getId())
                .bodyValue(updated)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .jsonPath("$.data").isNotEmpty()
                .jsonPath("$.links").isNotEmpty();
    }

    @Test
    void testPartialUpdateUser() {
        User updated = repository.save(FakerBlogData.user());
        updated.setFirstName("Jordi");
        updated.setPassword("ChangeMyMind");

        client.patch().uri("/users/" + updated.getId())
                .bodyValue(updated)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .jsonPath("$.data").isNotEmpty()
                .jsonPath("$.data.firstName").isEqualTo("Jordi")
                .jsonPath("$.links").isNotEmpty();

    }

    @Test
    void testGetAllPostOfUser() {
        User user = this.repository.save(FakerBlogData.user());
        for (int i = 0; i < 10; i++) {
            this.postRepository.save(FakerBlogData.post(user));
        }

        client.get().uri("/users/" + user.getId() + "/posts")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .jsonPath("$.data.length()").isEqualTo(10)
                .jsonPath("$.data[0].author").isEqualTo(user.getUsername())
                .jsonPath("$.links").isNotEmpty()
                .jsonPath("$.metadata").isNotEmpty();
    }

    @Test
    void testGetAllCommentsOfUser() {
        User user = this.repository.save(FakerBlogData.user());
        for (int i = 0; i < 5; i++) {
            Post post = this.postRepository.save(FakerBlogData.post(user));
            for (int j = 0; j < 10; j++) {
                this.commentRepository.save(FakerBlogData.comment(user, post));
            }
        }

        client.get().uri("/users/" + user.getId() + "/comments")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .jsonPath("$.data[0].author").isEqualTo(user.getUsername())
                .jsonPath("$.links").isNotEmpty()
                .jsonPath("$.metadata").isNotEmpty();
    }

    @Test
    void testGetAllCommentsOfPost() {
        User user = this.repository.save(FakerBlogData.user());
        Post post = this.postRepository.save(FakerBlogData.post(user));
        var randomCountComments = random.nextInt(1, 5);
        for (int j = 0; j < randomCountComments; j++) {
            this.commentRepository.save(FakerBlogData.comment(user, post));
        }

        client.get().uri("/users/" + user.getId() + "/posts/" + post.getId() + "/comments")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .jsonPath("$.data.length()").isEqualTo(randomCountComments)
                .jsonPath("$.data[0].author").isEqualTo(user.getUsername())
                .jsonPath("$.links").isNotEmpty()
                .jsonPath("$.metadata").isNotEmpty();
    }

}
