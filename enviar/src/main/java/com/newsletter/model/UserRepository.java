package com.newsletter.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<Object, Long> {

    @Query(value = "SELECT email FROM users WHERE validade > CURRENT_DATE", nativeQuery = true)
    List<String> getValidEmails();
}
