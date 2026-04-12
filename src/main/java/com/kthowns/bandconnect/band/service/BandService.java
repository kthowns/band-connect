package com.kthowns.bandconnect.band.service;

import com.kthowns.bandconnect.band.dto.BandDetail;
import com.kthowns.bandconnect.band.dto.MemberDetail;
import com.kthowns.bandconnect.band.entity.Band;
import com.kthowns.bandconnect.band.entity.BandMember;
import com.kthowns.bandconnect.band.repository.BandMemberRepository;
import com.kthowns.bandconnect.band.repository.BandRepository;
import com.kthowns.bandconnect.common.exception.CustomException;
import com.kthowns.bandconnect.common.exception.CustomResponseCode;
import com.kthowns.bandconnect.user.dto.UserDto;
import com.kthowns.bandconnect.user.entity.User;
import com.kthowns.bandconnect.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    @Transactional(readOnly = true)
    public List<BandDetail> getBelongingBands(User user) {
        List<BandMember> myRoles = bandMemberRepository.findByMemberWithBands(user.getId());
        List<Band> myBands = myRoles.stream()
                .map(BandMember::getBand)
                .distinct()
                .toList();
        List<Long> bandIds = myBands.stream()
                .map(Band::getId).toList();
        List<BandMember> allMembers = bandMemberRepository.findAllByBandIdInWithMember(bandIds);

        Map<Long, List<MemberDetail>> membersByBand = allMembers.stream()
                .collect(Collectors.groupingBy(
                        bm -> bm.getBand().getId(),
                        Collectors.mapping(
                                bm -> MemberDetail.from(bm.getMember(), bm),
                                Collectors.collectingAndThen(
                                        Collectors.toCollection(LinkedHashSet::new),
                                        ArrayList::new
                                )
                        )
                ));

        System.out.println("Some asd");

        return myBands.stream()
                .map(band -> BandDetail.builder()
                        .id(band.getId())
                        .name(band.getName())
                        .description(band.getDescription())
                        .leader(UserDto.fromEntity(band.getLeader()))
                        .members(membersByBand.getOrDefault(band.getId(), List.of()))
                        .createdAt(band.getCreatedAt())
                        .build())
                .toList();
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

        bandMemberRepository.save(BandMember.builder()
                .member(user)
                .position("Leader")
                .band(band)
                .build());
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
