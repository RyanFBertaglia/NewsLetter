package com.newsletter.repository;

import com.newsletter.model.Rating;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RatingRepository extends JpaRepository<Rating, Long> {

    @Override
    List<Rating> findAll();

    Optional<Rating> findByIdUserAndDia(long id, String dia);

}
