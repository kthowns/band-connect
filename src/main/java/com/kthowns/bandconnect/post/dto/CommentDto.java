package com.kthowns.bandconnect.post.dto;

import com.kthowns.bandconnect.post.entity.Comment;
import com.kthowns.bandconnect.user.dto.UserDto;
import com.kthowns.bandconnect.user.entity.User;
import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentDto {
    private Integer id;
    private UserDto author;
    private String content;
    private Timestamp createdAt;

    static public CommentDto fromEntity(Comment comment) {
        User user = comment.getAuthor();

        return CommentDto.builder()
                .id(comment.getId())
                .author(user != null ? UserDto.fromEntity(user) : null)
                .content(comment.getContent())
                .createdAt(comment.getCreatedAt())
                .build();
    }
}
