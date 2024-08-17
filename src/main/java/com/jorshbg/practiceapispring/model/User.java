package com.jorshbg.practiceapispring.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "users")
public class User implements Serializable {

    @Serial
    private static final long serialVersionUID = 6845031328595617221L;

    //region Properties
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 40)
    @Column(unique = true, nullable = false)
    private String username;

    @NotNull
    @Size(max = 60)
    private String password;

    @NotNull
    @Size(max = 50)
    @Column(name = "first_name")
    private String firstName;

    @Size(max = 50)
    @NotNull
    @Column(name = "last_name")
    private String lastName;

    @NotNull
    @Email
    private String email;

    @Size(min = 5, max = 25)
    private String phone;

    private LocalDate birthday;

    private String biography;

    @Column(name = "remember_token")
    private String rememberToken;
    //endregion

    //region Relationships
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "author")
    private List<Post> posts;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "author")
    private List<Comment> comments;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "users")
    private List<Post> likes;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<ChangeLog> changeLogs;
    //endregion

    //region timestamps
    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "last_modified_at")
    private LocalDateTime lastModifiedAt;
    //endregion
}
