package com.newsletter.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Table(name = "avaliacao")
@Data
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "dia")
    private LocalDate dia;

    @Column(name = "id-user")
    private long idUser;

    @Column(name = "comentario")
    private String comentario;

    @Column(name = "media")
    private int media;
}