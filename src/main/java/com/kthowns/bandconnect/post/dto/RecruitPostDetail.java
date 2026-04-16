package com.kthowns.bandconnect.post.dto;

import com.kthowns.bandconnect.band.dto.BandDto;
import com.kthowns.bandconnect.recruit.dto.RecruitDto;
import com.kthowns.bandconnect.user.dto.UserDto;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RecruitPostDetail {
    private Long id;
    private UserDto author;
    private BandDto band;
    private String title;
    private String content;
    private Long views;
    private LocalDateTime createdAt;
    private Long applicantCount;
    private List<HashtagDto> hashtags;
    private List<RecruitDto> recruits;
    private List<CommentDto> comments;

    public boolean isClosed() {
        if (recruits == null || recruits.isEmpty()) return false;
        return recruits.stream().allMatch(r -> r.getMember() != null);
    }
}
