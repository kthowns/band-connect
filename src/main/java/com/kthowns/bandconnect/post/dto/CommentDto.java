package com.kthowns.bandconnect.post.dto;

import com.kthowns.bandconnect.post.entity.Comment;
import com.kthowns.bandconnect.user.dto.UserDto;
import com.kthowns.bandconnect.user.entity.User;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentDto {
    private Long id;
    private RecruitPostDto post;
    private UserDto author;
    private String content;
    private LocalDateTime createdAt;

    static public CommentDto fromEntity(Comment comment) {
        User user = comment.getAuthor();

        return CommentDto.builder()
                .id(comment.getId())
                .post(RecruitPostDto.fromEntity(comment.getPost()))
                .author(user != null ? UserDto.fromEntity(user) : null)
                .content(comment.getContent())
                .createdAt(comment.getCreatedAt())
                .build();
    }
}
