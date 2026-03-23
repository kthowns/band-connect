package com.kthowns.bandconnect.band.entity;

import com.kthowns.bandconnect.user.entity.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "band_members")
@EntityListeners(AuditingEntityListener.class)
public class BandMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "band_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Band band;

    @Column(name = "position", nullable = false)
    private String position;

    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
