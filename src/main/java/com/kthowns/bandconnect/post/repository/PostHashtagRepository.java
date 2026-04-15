package com.kthowns.bandconnect.post.repository;

import com.kthowns.bandconnect.post.entity.Hashtag;
import com.kthowns.bandconnect.post.entity.PostHashtag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostHashtagRepository extends JpaRepository<PostHashtag, Long> {
    @Query("SELECT ph from PostHashtag ph" +
            " JOIN FETCH ph.hashtag" +
            " WHERE ph.recruitPost.id IN :recruitPostIds")
    List<PostHashtag> findByRecruitPostIds(@Param("recruitPostIds") List<Long> recruitPostIds);

    @Query("SELECT ph.hashtag from PostHashtag ph" +
            " WHERE ph.recruitPost.id = :postId")
    List<Hashtag> findHashtagsByPostId(@Param("postId") Long postId);

    void deleteByRecruitPostId(Long recruitPostId);
}
