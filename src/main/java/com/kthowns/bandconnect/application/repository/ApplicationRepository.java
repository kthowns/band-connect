package com.kthowns.bandconnect.application.repository;

import com.kthowns.bandconnect.application.dto.PostApplicantCount;
import com.kthowns.bandconnect.application.entity.Application;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long> {
    @Query("SELECT new com.kthowns.bandconnect.application.dto.PostApplicantCount(r.recruitPost.id, COUNT(ap)) " +
            " FROM Application ap" +
            " JOIN ap.recruit r" +
            " WHERE r.recruitPost.id IN :postIds" +
            " GROUP BY r.recruitPost.id")
    List<PostApplicantCount> countByRecruitPostIds(@Param("postIds") List<Long> postIds);

    Long countByRecruit_RecruitPost_Id(Long recruitPostId);

    List<Application> findByApplicant_Id(Long applicantId);

    List<Application> findByRecruit_IdIn(Collection<Long> recruitIds);

    Optional<Application> findByIdAndRecruit_RecruitPost_Author_Id(Long id, Long recruitRecruitPostAuthorId);

    List<Application> findByRecruit_IdAndIdNot(Long recruitId, Long id);

    boolean existsByRecruit_IdAndApplicant_Id(Long recruitId, Long applicantId);
}
