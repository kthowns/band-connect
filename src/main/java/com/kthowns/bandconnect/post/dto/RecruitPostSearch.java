package com.kthowns.bandconnect.post.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RecruitPostSearch {
    private List<String> hashtags;
    private String keyword;
}
