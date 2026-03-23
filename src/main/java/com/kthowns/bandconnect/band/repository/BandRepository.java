package com.kthowns.bandconnect.band.repository;

import com.kthowns.bandconnect.band.entity.Band;
import com.kthowns.bandconnect.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BandRepository extends JpaRepository<Band, Long> {
    List<Band> findByLeader(User leader);

    boolean existsByName(String name);

    Optional<Band> findByIdAndLeader_Id(Long id, Long leaderId);
}
