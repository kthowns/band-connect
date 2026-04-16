package com.kthowns.bandconnect.post.repository;

import com.kthowns.bandconnect.post.dto.RecruitPostDto;
import com.kthowns.bandconnect.post.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Query("SELECT c.post.id as postId, COUNT(c) as commentCount FROM Comment c" +
            " WHERE c.post.id IN :recruitPostIds" +
            " GROUP BY c.post.id")
    List<RecruitPostDto.PostCommentCount> countByRecruitPostIds(@Param("recruitPostIds") List<Long> recruitPostIds);

    List<Comment> findByPostId(Long postId);

    @Query("SELECT c FROM Comment c" +
            " JOIN FETCH c.author" +
            " WHERE c.id = :id")
    Optional<Comment> findByIdWithAuthor(@Param("id") Long id);

    List<Comment> findByAuthor_Id(Long authorId);
}
