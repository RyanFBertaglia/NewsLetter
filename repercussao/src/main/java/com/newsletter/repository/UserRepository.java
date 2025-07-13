package com.newsletter.repository;

import com.newsletter.dto.UserShareDTO;
import com.newsletter.model.user.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<Users, Long> {

    @Query("UPDATE users SET comentarios += 1 WHERE id = :idUser")
    boolean incrementCommentByUser(@Param("idUser") long idUser);

    @Query("SELECT new com.exemplo.dto.UserShareDTO(u.id, u.compartilhados) FROM User u ORDER BY u.compartilhados DESC")
    List<UserShareDTO> findUsersShare();

}
