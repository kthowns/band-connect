package com.kthowns.bandconnect.band.repository;

import com.kthowns.bandconnect.band.entity.Band;
import com.kthowns.bandconnect.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BandRepository extends JpaRepository<Band, Long> {
    List<Band> findByLeader(User leader);

    boolean existsByName(String name);
}
