package com.newsletter.repository;

import com.newsletter.dto.UserCommentDTO;
import com.newsletter.model.feedback.Comments;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentsRepository extends JpaRepository<Comments, Long> {

    List<UserCommentDTO> findAllByVersion(long version);
}
