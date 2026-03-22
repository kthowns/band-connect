package com.kthowns.bandconnect.post.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddCommentRequest {
    private String content;
}
