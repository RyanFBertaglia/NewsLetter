package com.newsletter.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
public class Rate {

    public Rate(long version, LocalDate date_rate, int quantComments, double average) {
        this.version = version;
        this.date_rate = date_rate;
        this.quantComments = quantComments;
        this.average = average;
    }


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private Long version;

    private LocalDate date_rate;

    private int quantComments;
    private double average;

    public void updateAverage(double newGrade) {
        this.average = (this.average * this.quantComments + newGrade) / (this.quantComments + 1);
        this.quantComments += 1;
    }


}
