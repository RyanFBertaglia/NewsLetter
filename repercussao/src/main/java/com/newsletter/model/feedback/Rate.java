package com.newsletter.model.feedback;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Table(name = "ratings")
@Data
public class Rate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long version;

    private LocalDate date_rate;

    private int quantComments;
    private double average;
}