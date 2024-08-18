package com.jorshbg.practiceapispring.util;


import com.github.javafaker.Faker;
import com.jorshbg.practiceapispring.model.Comment;
import com.jorshbg.practiceapispring.model.Post;
import com.jorshbg.practiceapispring.model.User;

import java.time.LocalDate;
import java.util.Random;

public class FakerBlogData {

    private static final Faker faker = new Faker();
    private static final Random random = new Random();

    public static User user() {
        User user = new User();
        user.setUsername(faker.name().username());
        user.setFirstName(faker.name().firstName());
        user.setLastName(faker.name().lastName());
        user.setEmail(faker.internet().safeEmailAddress());
        user.setPassword(faker.internet().password());
        user.setBiography(faker.lorem().sentence());
        user.setBirthday(LocalDate.of(random.nextInt(1980, 2010), random.nextInt(1, 12), random.nextInt(1, 28)));
        user.setPhone(faker.phoneNumber().cellPhone());
        user.setRememberToken(faker.lorem().sentence());
        return user;
    }

    public static Comment comment() {
        return comment(user());
    }

    public static Comment comment(User author) {
        return comment(author, post());
    }

    public static Comment comment(Post post){
        return comment(user(), post);
    }

    public static Comment comment(User author, Post post) {
        Comment comment = new Comment();
        comment.setContent(faker.lorem().sentence(5));
        comment.setPost(post);
        comment.setAuthor(author);
        return comment;
    }

    public static Post post(User author) {
        Post post = new Post();
        post.setContent(faker.lorem().paragraph(40));
        post.setTitle(faker.lorem().sentence(5));
        post.setAuthor(author);
        return post;
    }

    public static Post post() {
        return post(user());
    }

}
