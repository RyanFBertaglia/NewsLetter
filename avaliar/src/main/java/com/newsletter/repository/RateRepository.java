package com.newsletter.repository;

import com.newsletter.model.Rate;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface RateRepository extends JpaRepository<Rate, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select r from Rate r where r.version = :version")
    Optional<Rate> findByVersionForUpdate(Long version);

}
