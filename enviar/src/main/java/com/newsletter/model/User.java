package com.newsletter.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.time.LocalDate;

@Entity
public class User {

    @Id @GeneratedValue
    Long id;

    private String email;
    private LocalDate validade;
}
