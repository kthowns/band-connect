package com.kthowns.bandconnect.post.dto;

import com.kthowns.bandconnect.recruit.dto.RecruitDetail;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostWithRecruits {
    private Long id;
    private String title;
    private String bandName;
    private LocalDateTime createdAt;
    List<RecruitDetail> recruits;
}
