package com.kthowns.bandconnect.post.repository;

import com.kthowns.bandconnect.post.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Query("SELECT c FROM Comment c" +
            " JOIN c.post p" +
            " WHERE p.id IN :recruitPostIds")
    List<Comment> findByRecruitPostIds(@Param("recruitPostIds") List<Long> recruitPostIds);

    List<Comment> findByPostId(Long postId);
}
