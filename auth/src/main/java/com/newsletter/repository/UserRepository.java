package com.newsletter.repository;

import com.newsletter.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    User getUserByEmail(String email);
    @Query("SELECT u.id FROM User u WHERE u.email = :email")
    Long getIdByEmail(@Param("email") String email);
}
