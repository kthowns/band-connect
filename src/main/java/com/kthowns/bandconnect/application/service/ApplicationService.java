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

        if(recruit.getRecruitPost().getAuthor().getId().equals(user.getId())) {
            throw new CustomException(CustomResponseCode.APPLY_RECURSIVE_ERROR);
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
    }
}
