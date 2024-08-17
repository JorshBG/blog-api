package com.jorshbg.practiceapispring.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "logs")
public class Log {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String message;

    @NotNull
    private String level;

    private String throwableStackTrace;

    //region Timestamps
    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    //endregion

}
