package com.kthowns.bandconnect.post.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RecruitPostSimpleDto {
    private Long id;
    private String authorName;
    private String bandName;
    private String title;
    private Long views;
    private LocalDateTime createdAt;
    private Long applicantCount;
    private List<String> hashtags;
    private List<String> recruitParts;
    private Long commentCount;

    public boolean isClosed() {
        return recruitParts == null || recruitParts.isEmpty();
    }
}
