package com.kthowns.bandconnect.post.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddPostRequest {
    private String title;
    private Long bandId;
    private String content;
    private String hashtag;
    private List<String> parts;
}
