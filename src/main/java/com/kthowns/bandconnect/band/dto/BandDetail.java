package com.kthowns.bandconnect.band.dto;

import com.kthowns.bandconnect.user.dto.UserDto;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BandDetail {
    private Long id;
    private String name;
    private String description;
    private UserDto leader;
    private List<MemberDetail> members;
    private LocalDateTime createdAt;
}
