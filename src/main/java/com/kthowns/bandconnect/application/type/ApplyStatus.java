package com.kthowns.bandconnect.application.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ApplyStatus {
    APPLIED("지원"),
    REJECTED("거절"),
    ACCEPTED("수락"),
    CLOSED("마감");

    private final String description;
}
