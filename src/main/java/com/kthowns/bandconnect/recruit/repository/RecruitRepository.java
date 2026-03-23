package com.kthowns.bandconnect.recruit.repository;

import com.kthowns.bandconnect.recruit.entity.Recruit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RecruitRepository extends JpaRepository<Recruit, Long> {
    @Query("SELECT r from Recruit r" +
            " WHERE r.recruitPost.id IN :recruitPostIds")
    List<Recruit> findByRecruitPostIds(@Param("recruitPostIds") List<Long> recruitPostIds);

    List<Recruit> findByRecruitPost_Id(Long recruitPostId);

    @Query("SELECT r FROM Recruit r" +
            " JOIN FETCH r.recruitPost rp" +
            " JOIN FETCH rp.author" +
            " WHERE r.id = :id")
    Optional<Recruit> findByIdWithPostAndAuthor(@Param("id") Long id);
}
