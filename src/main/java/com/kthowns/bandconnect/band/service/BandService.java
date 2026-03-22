package com.kthowns.bandconnect.band.service;

import com.kthowns.bandconnect.band.entity.Band;
import com.kthowns.bandconnect.band.repository.BandRepository;
import com.kthowns.bandconnect.common.exception.CustomException;
import com.kthowns.bandconnect.common.exception.CustomResponseCode;
import com.kthowns.bandconnect.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BandService {
    private final BandRepository bandRepository;

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
}
