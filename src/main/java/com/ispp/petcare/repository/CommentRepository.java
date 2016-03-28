package com.ispp.petcare.repository;

import com.ispp.petcare.domain.Comment;

import org.springframework.data.jpa.repository.*;

import java.util.Collection;
import java.util.List;

/**
 * Spring Data JPA repository for the Comment entity.
 */
public interface CommentRepository extends JpaRepository<Comment,Long> {

    /*
    @Query("select comment from Comment comment where comment.user.login = ?#{principal.username}")
    List<Comment> findByUserIsCurrentUser();*/

    @Query("select c from Comment c where c.complaint.id=?1")
    Collection<Comment> findCommentByComplaintId(Long id);

}
