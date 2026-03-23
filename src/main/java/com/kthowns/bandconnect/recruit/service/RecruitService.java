package com.kthowns.bandconnect.recruit.service;

import com.kthowns.bandconnect.common.exception.CustomException;
import com.kthowns.bandconnect.common.exception.CustomResponseCode;
import com.kthowns.bandconnect.post.entity.RecruitPost;
import com.kthowns.bandconnect.recruit.entity.Recruit;
import com.kthowns.bandconnect.recruit.repository.RecruitRepository;
import com.kthowns.bandconnect.user.entity.User;
import com.kthowns.bandconnect.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class RecruitService {
    private final RecruitRepository recruitRepository;
    private final UserRepository userRepository;

    @Transactional
    public void createRecruits(RecruitPost post, List<String> parts) {
        for (String part : parts) {
            recruitRepository.save(
                    Recruit.builder()
                            .position(part)
                            .recruitPost(post)
                            .build()
            );
        }
    }

    @Transactional
    public Recruit confirmRecruit(Long recruitId, Long applicantId, Long userId) {
        log.info("confirmRecruit() Init");
        Recruit recruit = recruitRepository
                .findByIdAndRecruitPost_Author_Id(recruitId, userId)
                .orElseThrow(() -> new CustomException(CustomResponseCode.RECRUIT_NOT_FOUND));

        log.info("confirmRecruit().applicantLoading applicantId: {}", applicantId);
        User applicant = userRepository.findById(applicantId)
                .orElseThrow(() -> new CustomException(CustomResponseCode.USER_NOT_FOUND));

        recruit.setMember(applicant);

        return recruitRepository.save(recruit);
    }
}
