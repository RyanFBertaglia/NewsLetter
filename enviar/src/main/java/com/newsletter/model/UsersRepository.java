package com.newsletter.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsersRepository extends JpaRepository<User, Long> {

    @Query(value = "SELECT email FROM users WHERE validade > CURRENT_DATE", nativeQuery = true)
    List<String> getValidEmails();
}
