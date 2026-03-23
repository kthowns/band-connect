package com.kthowns.bandconnect.application.repository;

import com.kthowns.bandconnect.application.dto.ApplicationDto;
import com.kthowns.bandconnect.application.dto.PostApplicantCount;
import com.kthowns.bandconnect.application.entity.Application;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long> {
    @Query("SELECT new com.kthowns.bandconnect.application.dto.PostApplicantCount(r.recruitPost.id, COUNT(ap)) " +
            " FROM Application ap" +
            " JOIN ap.recruit r" +
            " WHERE r.recruitPost.id IN :postIds" +
            " GROUP BY r.recruitPost.id")
    List<PostApplicantCount> countByRecruitPostIds(@Param("postIds") List<Long> postIds);

    Long countByRecruitRecruitPost_Id(Long recruitPostId);

    List<Application> findByApplicant_Id(Long applicantId);
}
