package com.newsletter.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
public class Rate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long version;

    private LocalDate date_rate;

    private int quantComments;
    private double average;

    public void updateAverage(double newGrade) {
        this.average = (this.average * this.quantComments + newGrade) / (this.quantComments + 1);
        this.quantComments += 1;
    }
}
