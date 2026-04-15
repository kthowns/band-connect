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
        if (parts.isEmpty()) {
            return;
        }
        parts = parts.stream().distinct().toList();

        if (recruitRepository.existsByPositionsAndBandId(parts, post.getBand().getId())) {
            throw new CustomException(CustomResponseCode.DUPLICATED_RECRUIT_POSITION);
        }

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

    @Transactional
    public void updateRecruits(RecruitPost post, List<String> parts) {
        List<Recruit> existingRecruits = recruitRepository.findByRecruitPost_Id(post.getId());

        // 멤버가 없는(Open) 구인만 필터링해서 삭제
        List<Recruit> openRecruits = existingRecruits.stream()
                .filter(r -> r.getMember() == null)
                .collect(java.util.stream.Collectors.toList());
        recruitRepository.deleteAll(openRecruits);

        // 이미 멤버가 있는 포지션 이름들
        java.util.Set<String> closedPositions = existingRecruits.stream()
                .filter(r -> r.getMember() != null)
                .map(Recruit::getPosition)
                .collect(java.util.stream.Collectors.toSet());

        // 새로운 파트들 중 이미 닫힌 포지션과 겹치지 않는 것만 추가
        List<String> partsToCreate = parts.stream()
                .filter(p -> !closedPositions.contains(p))
                .distinct()
                .toList();

        for (String part : partsToCreate) {
            recruitRepository.save(
                    Recruit.builder()
                            .position(part)
                            .recruitPost(post)
                            .build()
            );
        }
    }
}
