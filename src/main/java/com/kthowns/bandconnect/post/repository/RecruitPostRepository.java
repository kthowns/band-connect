package com.kthowns.bandconnect.post.repository;

import com.kthowns.bandconnect.post.entity.RecruitPost;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @Query(value = "SELECT DISTINCT rp from RecruitPost rp" +
            " JOIN PostHashtag ph on ph.recruitPost = rp" +
            " JOIN ph.hashtag h" +
            " JOIN FETCH rp.author" +
            " JOIN FETCH rp.band" +
            " WHERE (rp.title LIKE %:keyword% OR rp.content LIKE %:keyword%) " +
            " AND h.name IN :hashtags",
            countQuery = "SELECT COUNT(DISTINCT rp) FROM RecruitPost rp " +
                    "JOIN PostHashtag ph ON ph.recruitPost = rp " +
                    "JOIN ph.hashtag h " +
                    "WHERE (rp.title LIKE %:keyword% OR rp.content LIKE %:keyword%) " +
                    "AND h.name IN :hashtags")
    Page<RecruitPost> findByKeywordAndHashtags(
            @Param("keyword") String keyword,
            @Param("hashtags") List<String> hashtags,
            Pageable pageable
    );

    @Query(value = "SELECT rp from RecruitPost rp" +
            " JOIN FETCH rp.author" +
            " JOIN FETCH rp.band" +
            " WHERE (rp.title LIKE %:keyword% OR rp.content LIKE %:keyword%)",
            countQuery = "SELECT COUNT(rp) from RecruitPost rp" +
                    " WHERE (rp.title LIKE %:keyword% OR rp.content LIKE %:keyword%)")
    Page<RecruitPost> findByKeyword(
            @Param("keyword") String keyword,
            Pageable pageable
    );

    @Query(value = "SELECT DISTINCT rp from RecruitPost rp" +
            " JOIN PostHashtag ph on ph.recruitPost = rp" +
            " JOIN ph.hashtag h" +
            " JOIN FETCH rp.author" +
            " JOIN FETCH rp.band" +
            " WHERE h.name IN :hashtags",
            countQuery = "SELECT COUNT(DISTINCT rp) from RecruitPost rp" +
                    " JOIN PostHashtag ph on ph.recruitPost = rp" +
                    " JOIN ph.hashtag h" +
                    " WHERE h.name IN :hashtags")
    Page<RecruitPost> findByHashtags(
            @Param("hashtags") List<String> hashtags,
            Pageable pageable
    );

    @Query(value = "SELECT rp FROM RecruitPost rp" +
            " JOIN FETCH rp.band" +
            " WHERE rp.author.id = :authorId",
            countQuery = "SELECT COUNT(rp) FROM RecruitPost rp" +
                    " WHERE rp.author.id = :authorId")
    Page<RecruitPost> findByAuthorIdWithBand(
            @Param("authorId") Long authorId,
            Pageable pageable
    );

    @Query(value = "SELECT rp FROM RecruitPost rp" +
            " JOIN FETCH rp.band" +
            " JOIN FETCH rp.author",
            countQuery = "SELECT COUNT(rp) FROM RecruitPost rp")
    Page<RecruitPost> findAllWithBandAndAuthor(Pageable pageable);

    @Query("SELECT rp FROM RecruitPost rp" +
            " JOIN FETCH rp.author" +
            " WHERE rp.id = :id")
    Optional<RecruitPost> findByIdWithAuthor(@Param("id") Long id);

    @Modifying(clearAutomatically = true) // Modify 후 영속성 컨테이너 초기화
    @Query("UPDATE RecruitPost rp SET rp.views = rp.views + 1 WHERE rp.id = :id")
    void incrementViews(@Param("id") Long id);
}
