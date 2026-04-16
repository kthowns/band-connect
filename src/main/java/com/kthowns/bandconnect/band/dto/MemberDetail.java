package com.kthowns.bandconnect.band.dto;

import com.kthowns.bandconnect.band.entity.BandMember;
import com.kthowns.bandconnect.user.entity.User;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberDetail {
    private Long id;
    private String username;
    private String name;
    private String position;
    private LocalDateTime joinAt;

    static public MemberDetail from(final User user, BandMember member) {
        return MemberDetail.builder()
                .id(user.getId())
                .username(user.getUsername())
                .name(user.getName())
                .position(member.getPosition())
                .joinAt(member.getCreatedAt())
                .build();
    }
}
