package com.kthowns.bandconnect.recruit.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ConfirmApplicationRequest {
    private String isAccepted;
    private Long applicationId;
}
