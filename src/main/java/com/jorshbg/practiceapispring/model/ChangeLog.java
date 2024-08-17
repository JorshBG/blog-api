package com.jorshbg.practiceapispring.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "changelogs")
public class ChangeLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "class_affected_name")
    private String classAffectedName;

    @NotNull
    @Column(name = "change_type")
    private String changeType;

    @NotNull
    private String description;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "user_id")
    private User user;

    //region Timestamps
    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    //endregion

}
