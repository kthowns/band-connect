package com.kthowns.bandconnect.application.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostApplicantCount {
    Long postId;
    Long count;
}
