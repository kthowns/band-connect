package com.kthowns.bandconnect.post.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EditPostRequest {
    private String title;
    private String content;
    private String hashtag;
    private List<String> parts;
}
