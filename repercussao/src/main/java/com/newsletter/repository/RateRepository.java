package com.newsletter.repository;

import com.newsletter.model.feedback.Rate;
import com.newsletter.dto.RateAverageDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface RateRepository extends JpaRepository<Rate, Long> {

    @Query("SELECT average FROM Rate r WHERE r.version = :version")
    Double findAverageMediaByDia(@Param("version") Long version);

    @Query("SELECT date_rate, average FROM Rate r WHERE r.date_rate = :dia")
    List<RateAverageDTO> findMessagesByDay(@Param("dia") LocalDate dia);

    Rate findByVersion(long version);
}
