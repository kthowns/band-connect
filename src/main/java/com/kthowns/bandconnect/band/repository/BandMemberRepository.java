package com.kthowns.bandconnect.band.repository;

import com.kthowns.bandconnect.band.entity.BandMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BandMemberRepository extends JpaRepository<BandMember, Long> {
    boolean existsByIdAndMember_IdAndPosition(Long bandId, Long memberId, String position);
}
