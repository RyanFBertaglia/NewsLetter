package com.newsletter.repository.user;

import com.newsletter.dto.UserShareDTO;
import com.newsletter.model.user.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {

    @Modifying
    @Transactional
    @Query("UPDATE Users u SET u.compartilhados = u.compartilhados + 1 WHERE u.id = :idUser")
    void incrementSharesByUser(@Param("idUser") long idUser);

    @Query("SELECT u.id, u.compartilhados FROM Users u ORDER BY u.compartilhados DESC")
    List<UserShareDTO> findUsersShare();

}
