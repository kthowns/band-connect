package com.kthowns.bandconnect.recruit.dto;

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
    private String position;
    private UserDto member;
    private LocalDateTime createdAt;

    static public RecruitDto fromEntity(Recruit recruit) {
        User member = recruit.getMember();
        return RecruitDto.builder()
                .id(recruit.getId())
                .position(recruit.getPosition())
                .member(member != null ? UserDto.fromEntity(member) : null)
                .createdAt(recruit.getCreatedAt())
                .build();
    }
}
