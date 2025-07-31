package com.newsletter.repository.feedback;

import com.newsletter.dto.UserCommentDTO;
import com.newsletter.model.feedback.Comments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentsRepository extends JpaRepository<Comments, Long> {

    List<UserCommentDTO> findByVersion(long version);
}
