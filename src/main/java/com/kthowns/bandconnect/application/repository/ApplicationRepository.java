package com.kthowns.bandconnect.application.repository;

import com.kthowns.bandconnect.application.entity.Application;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long> {
    Long countByRecruit_RecruitPost_Id(Long recruitPostId);

    List<Application> findByApplicant_Id(Long applicantId);

    List<Application> findByRecruit_IdIn(Collection<Long> recruitIds);

    Optional<Application> findByIdAndRecruit_RecruitPost_Author_Id(Long id, Long recruitRecruitPostAuthorId);

    List<Application> findByRecruit_IdAndIdNot(Long recruitId, Long id);

    boolean existsByRecruit_IdAndApplicant_Id(Long recruitId, Long applicantId);

    long countByRecruit_Id(Long recruitId);
}
