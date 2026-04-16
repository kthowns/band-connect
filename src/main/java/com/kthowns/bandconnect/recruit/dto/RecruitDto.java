package com.kthowns.bandconnect.recruit.dto;

import com.kthowns.bandconnect.post.dto.RecruitPostDto;
import com.kthowns.bandconnect.recruit.entity.Recruit;
import com.kthowns.bandconnect.user.dto.UserDto;
import com.kthowns.bandconnect.user.entity.User;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RecruitDto {
    private Long id;
    private RecruitPostDto post;
    private String position;
    private UserDto member;
    private Long applicantCount;
    private LocalDateTime createdAt;

    static public RecruitDto fromEntity(Recruit recruit) {
        User member = recruit.getMember();
        return RecruitDto.builder()
                .id(recruit.getId())
                .post(RecruitPostDto.fromEntity(recruit.getRecruitPost()))
                .position(recruit.getPosition())
                .applicantCount(recruit.getApplicantCount())
                .member(member != null ? UserDto.fromEntity(member) : null)
                .createdAt(recruit.getCreatedAt())
                .build();
    }
}
