package com.kthowns.bandconnect.application.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApplyRecruitRequest {
    private Long recruitId;
    private Integer age;
    private String location;
    private String phone;
    private String description;
}
