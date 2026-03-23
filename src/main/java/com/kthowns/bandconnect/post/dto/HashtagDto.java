package com.kthowns.bandconnect.post.dto;

import com.kthowns.bandconnect.post.entity.Hashtag;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HashtagDto {
    private Long id;
    private String name;

    static public HashtagDto fromEntity(Hashtag hashtag) {
        return HashtagDto.builder()
                .id(hashtag.getId())
                .name(hashtag.getName())
                .build();
    }
}
