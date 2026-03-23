package com.kthowns.bandconnect.band.repository;

import com.kthowns.bandconnect.band.entity.BandMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BandMemberRepository extends JpaRepository<BandMember, Long> {
    boolean existsByIdAndMember_IdAndPosition(Long bandId, Long memberId, String position);

    @Query("SELECT bm FROM BandMember bm" +
            " JOIN FETCH bm.band" +
            " WHERE bm.member.id = :memberId")
    List<BandMember> findByMemberWithBands(@Param("memberId") Long memberId);

    @Query("SELECT bm FROM BandMember bm" +
            " JOIN FETCH bm.member" +
            " WHERE bm.band.id IN :bandIds")
    List<BandMember> findAllByBandIdInWithMember(List<Long> bandIds);
}
