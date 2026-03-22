package com.kthowns.bandconnect.post.repository;

import com.kthowns.bandconnect.post.entity.RecruitPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RecruitPostRepository extends JpaRepository<RecruitPost, Long> {

    @Query("SELECT rp FROM RecruitPost rp" +
            " JOIN FETCH rp.author" +
            " JOIN FETCH rp.band" +
            " WHERE rp.id = :postId")
    Optional<RecruitPost> findByIdWithAuthorAndBand(@Param("postId") Long postId);

    @Query("SELECT DISTINCT rp from RecruitPost rp" +
            " JOIN PostHashtag ph on ph.recruitPost = rp" +
            " JOIN ph.hashtag h" +
            " JOIN FETCH rp.author" +
            " JOIN FETCH rp.band" +
            " WHERE (rp.title LIKE %:keyword% OR rp.content LIKE %:keyword%) " +
            " AND h.name IN :hashtags")
    List<RecruitPost> findByKeywordAndHashtags(
            @Param("keyword") String keyword,
            @Param("hashtags") List<String> hashtags
    );

    @Query("SELECT rp from RecruitPost rp" +
            " JOIN FETCH rp.author" +
            " JOIN FETCH rp.band" +
            " WHERE (rp.title LIKE %:keyword% OR rp.content LIKE %:keyword%) ")
    List<RecruitPost> findByKeyword(@Param("keyword") String keyword);

    @Query("SELECT DISTINCT rp from RecruitPost rp" +
            " JOIN PostHashtag ph on ph.recruitPost = rp" +
            " JOIN ph.hashtag h" +
            " JOIN FETCH rp.author" +
            " JOIN FETCH rp.band" +
            " WHERE h.name IN :hashtags")
    List<RecruitPost> findByHashtags(@Param("hashtags") List<String> hashtags);

    @Query("SELECT rp FROM RecruitPost rp" +
            " JOIN FETCH rp.band" +
            " JOIN FETCH rp.author")
    List<RecruitPost> findAllWithBandAndAuthor();

    @Query("SELECT rp FROM RecruitPost rp" +
            " JOIN FETCH rp.author" +
            " WHERE rp.id = :id")
    Optional<RecruitPost> findByIdWithAuthor(@Param("id") Long id);

    @Modifying
    @Query("UPDATE RecruitPost rp SET rp.views = rp.views + 1 WHERE rp.id = :id")
    void incrementViews(@Param("id") Long id);
}
