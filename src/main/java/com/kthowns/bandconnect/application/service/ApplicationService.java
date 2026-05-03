package com.kthowns.bandconnect.application.service;

import com.kthowns.bandconnect.application.dto.ApplicationDto;
import com.kthowns.bandconnect.application.dto.ApplyRecruitRequest;
import com.kthowns.bandconnect.application.entity.Application;
import com.kthowns.bandconnect.application.repository.ApplicationRepository;
import com.kthowns.bandconnect.application.type.ApplyStatus;
import com.kthowns.bandconnect.common.exception.CustomException;
import com.kthowns.bandconnect.common.exception.CustomResponseCode;
import com.kthowns.bandconnect.recruit.entity.Recruit;
import com.kthowns.bandconnect.recruit.repository.RecruitRepository;
import com.kthowns.bandconnect.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ApplicationService {
    private final ApplicationRepository applicationRepository;
    private final RecruitRepository recruitRepository;

    @Transactional(readOnly = true)
    public List<ApplicationDto> getApplications(Long userId) {
        return applicationRepository.findByApplicant_Id(userId)
                .stream().map(ApplicationDto::fromEntity).toList();
    }

    @Transactional
    public void applyApplication(ApplyRecruitRequest request, User user) {
        Recruit recruit = recruitRepository.findByIdWithPostAndAuthor(request.getRecruitId())
                .orElseThrow(() -> new CustomException(CustomResponseCode.RECRUIT_NOT_FOUND));

        /* 자신의 구인글에 대한 지원 제한
        if (recruit.getRecruitPost().getAuthor().getId().equals(user.getId())) {
            throw new CustomException(CustomResponseCode.APPLY_RECURSIVE_ERROR);
        }
        */

        if (applicationRepository.existsByRecruit_IdAndApplicant_Id(recruit.getId(), user.getId())) {
            throw new CustomException(CustomResponseCode.DUPLICATED_APPLY_POSITION);
        }

        applicationRepository.save(
                Application.builder()
                        .applicant(user)
                        .recruit(recruit)
                        .age(request.getAge())
                        .phone(request.getPhone())
                        .description(request.getDescription())
                        .location(request.getLocation())
                        .status(ApplyStatus.APPLIED)
                        .build()
        );

        long applicantCount = applicationRepository.countByRecruit_RecruitPost_Id(recruit.getRecruitPost().getId());

        recruit.setApplicantCount(
                applicantCount
        );
    }

    @Transactional
    public Application acceptApplication(Long applicationId, Long userId) {
        Application application = applicationRepository
                .findByIdAndRecruit_RecruitPost_Author_Id(applicationId, userId)
                .orElseThrow(() -> new CustomException(CustomResponseCode.APPLICATION_NOT_FOUND));

        application.setStatus(ApplyStatus.ACCEPTED);

        List<Application> otherApplications = applicationRepository
                .findByRecruit_IdAndIdNot(application.getRecruit().getId(), applicationId);

        for (Application otherApplication : otherApplications) {
            if (!otherApplication.getStatus().equals(ApplyStatus.REJECTED)) {
                otherApplication.setStatus(ApplyStatus.CLOSED);
            }
        }

        return applicationRepository.save(application);
    }

    public void declineApplication(Long applicationId, Long userId) {
        Application application = applicationRepository
                .findByIdAndRecruit_RecruitPost_Author_Id(applicationId, userId)
                .orElseThrow(() -> new CustomException(CustomResponseCode.APPLICATION_NOT_FOUND));

        if (application.getStatus().equals(ApplyStatus.ACCEPTED)) {
            throw new CustomException(CustomResponseCode.APPLICATION_ALREADY_ACCEPTED);
        }
        application.setStatus(ApplyStatus.REJECTED);
    }
}
