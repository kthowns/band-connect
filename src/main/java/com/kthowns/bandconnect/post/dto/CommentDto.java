package com.kthowns.bandconnect.post.dto;

import com.kthowns.bandconnect.post.entity.Comment;
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
    private Long postId;
    private Long authorId;
    private String postTitle;
    private String authorName;
    private String content;
    private LocalDateTime createdAt;

    static public CommentDto fromEntity(Comment comment) {
        User user = comment.getAuthor();

        return CommentDto.builder()
                .id(comment.getId())
                .postId(comment.getPost().getId())
                .authorId(comment.getAuthor().getId())
                .postTitle(comment.getPost().getTitle())
                .authorName(user != null ? user.getName() : "탈퇴한 사용자")
                .content(comment.getContent())
                .createdAt(comment.getCreatedAt())
                .build();
    }
}
