package com.newsletter.repository;

import com.newsletter.model.Comments;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentsRepository extends JpaRepository<Comments, Long> {
    Optional<Comments> findByVersionAndIdUser(Long version, Long idUser);
}
