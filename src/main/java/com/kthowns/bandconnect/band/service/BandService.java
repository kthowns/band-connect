package com.kthowns.bandconnect.band.service;

import com.kthowns.bandconnect.band.entity.Band;
import com.kthowns.bandconnect.band.entity.BandMember;
import com.kthowns.bandconnect.band.repository.BandMemberRepository;
import com.kthowns.bandconnect.band.repository.BandRepository;
import com.kthowns.bandconnect.common.exception.CustomException;
import com.kthowns.bandconnect.common.exception.CustomResponseCode;
import com.kthowns.bandconnect.user.entity.User;
import com.kthowns.bandconnect.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BandService {
    private final BandRepository bandRepository;
    private final BandMemberRepository bandMemberRepository;
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public List<Band> getMyBands(User user) {
        return bandRepository.findByLeader(user);
    }

    @Transactional
    public void createBand(User user, String name, String description) {
        if (bandRepository.existsByName(name)) {
            throw new CustomException(CustomResponseCode.DUPLICATED_BAND);
        }

        Band band = Band.builder()
                .name(name)
                .description(description)
                .leader(user)
                .build();

        bandRepository.save(band);
    }

    public void addMember(Long bandId, Long applicantId, String position, Long userId) {
        Band band = bandRepository.findByIdAndLeader_Id(bandId, userId)
                .orElseThrow(() -> new CustomException(CustomResponseCode.BAND_NOT_FOUND));

        User applicant = userRepository.findById(applicantId)
                .orElseThrow(() -> new CustomException(CustomResponseCode.USER_NOT_FOUND));

        if (bandMemberRepository
                .existsByIdAndMember_IdAndPosition(bandId, applicantId, position)) {
            throw new CustomException(CustomResponseCode.DUPLICATED_MEMBER);
        }

        bandMemberRepository.save(
                BandMember.builder()
                        .band(band)
                        .member(applicant)
                        .position(position)
                        .build()
        );
    }
}
