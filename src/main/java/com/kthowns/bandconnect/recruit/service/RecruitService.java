package com.kthowns.bandconnect.recruit.service;

import com.kthowns.bandconnect.post.entity.RecruitPost;
import com.kthowns.bandconnect.recruit.entity.Recruit;
import com.kthowns.bandconnect.recruit.repository.RecruitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RecruitService {
    private final RecruitRepository recruitRepository;

    @Transactional
    public void createRecruits(RecruitPost post, List<String> parts) {
        for(String part : parts) {
            recruitRepository.save(
                    Recruit.builder()
                            .position(part)
                            .recruitPost(post)
                            .build()
            );
        }
    }
}
