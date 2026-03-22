package com.kthowns.bandconnect.application.entity;

import com.kthowns.bandconnect.application.type.ApplyStatus;
import com.kthowns.bandconnect.recruit.entity.Recruit;
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
@Table(name = "applications")
@EntityListeners(AuditingEntityListener.class)
public class Application {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recruit_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Recruit recruit;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "applicant_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User applicant;

    @Column(name = "age") // 개인정보는 포함하지 않을 수 있음
    private Integer age;

    @Column(name = "location")
    private String location;

    @Column(name = "phone")
    private String phone;

    @Column(name = "description")
    private String description;

    @Column(name = "status", nullable = false)
    private ApplyStatus status;

    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;
}