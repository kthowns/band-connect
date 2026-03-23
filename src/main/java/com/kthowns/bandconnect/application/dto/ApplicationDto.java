package com.kthowns.bandconnect.application.dto;

import com.kthowns.bandconnect.application.entity.Application;
import com.kthowns.bandconnect.application.type.ApplyStatus;
import com.kthowns.bandconnect.recruit.dto.RecruitDto;
import com.kthowns.bandconnect.user.dto.UserDto;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApplicationDto {
    private Long id;
    private RecruitDto recruit;
    private UserDto applicant;
    private Integer age;
    private String location;
    private String phone;
    private String description;
    private ApplyStatus status;
    private LocalDateTime createdAt;

    static public ApplicationDto fromEntity(Application application) {
        return ApplicationDto.builder()
                .id(application.getId())
                .recruit(RecruitDto.fromEntity(application.getRecruit()))
                .applicant(UserDto.fromEntity(application.getApplicant()))
                .age(application.getAge())
                .location(application.getLocation())
                .phone(application.getPhone())
                .description(application.getDescription())
                .status(application.getStatus())
                .createdAt(application.getCreatedAt())
                .build();
    }
}
