package com.kthowns.bandconnect.recruit.entity;

import com.kthowns.bandconnect.post.entity.RecruitPost;
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
@Table(name = "recruits")
@EntityListeners(AuditingEntityListener.class)
public class Recruit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    @OnDelete(action = OnDeleteAction.SET_NULL)
    private RecruitPost recruitPost;

    @Column(name = "position", nullable = false)
    private String position;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    @OnDelete(action = OnDeleteAction.SET_NULL)
    private User member;

    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
