package com.newsletter.model.feedback;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
public class Comments {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private long version;

    @Column(nullable = false)
    private LocalDate dia;

    @Column(name = "user_id", nullable = false)
    private Long idUser;

    @Column(nullable = false)
    private String message;

    private double average;
}
