package com.kthowns.bandconnect.band.dto;

import com.kthowns.bandconnect.band.entity.Band;
import com.kthowns.bandconnect.user.dto.UserDto;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BandDto {
    private Long id;
    private String name;
    private String description;
    private UserDto leader;
    private LocalDateTime createdAt;

    static public BandDto fromEntity(Band band) {
        return BandDto.builder()
                .id(band.getId())
                .name(band.getName())
                .description(band.getDescription())
                .leader(UserDto.fromEntity(band.getLeader()))
                .createdAt(band.getCreatedAt())
                .build();
    }
}
