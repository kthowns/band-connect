package com.kthowns.bandconnect.recruit.dto;

import com.kthowns.bandconnect.application.dto.ApplicationDto;
import com.kthowns.bandconnect.post.dto.RecruitPostDto;
import com.kthowns.bandconnect.user.dto.UserDto;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RecruitDetail {
    private Long id;
    private RecruitPostDto post;
    private String position;
    private UserDto member;
    private LocalDateTime createdAt;
    private List<ApplicationDto> applications;
}
