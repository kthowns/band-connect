package com.kthowns.bandconnect.post.dto;

import com.kthowns.bandconnect.band.dto.BandDto;
import com.kthowns.bandconnect.post.entity.RecruitPost;
import com.kthowns.bandconnect.user.dto.UserDto;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RecruitPostDto {
    private Long id;
    private UserDto author;
    private BandDto band;
    private String title;
    private String content;
    private Long views;
    private LocalDateTime createdAt;

    static public RecruitPostDto fromEntity(RecruitPost recruitPost) {
        return RecruitPostDto.builder()
                .id(recruitPost.getId())
                .author(UserDto.fromEntity(recruitPost.getAuthor()))
                .band(BandDto.fromEntity(recruitPost.getBand()))
                .title(recruitPost.getTitle())
                .content(recruitPost.getContent())
                .views(recruitPost.getViews())
                .createdAt(recruitPost.getCreatedAt())
                .build();
    }
}
