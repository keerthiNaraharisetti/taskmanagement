package com.taskmanagement.app.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TaskStatus {
    PENDING(1, "Pending"),
    IN_PROGRESS(2, "In Progress"),
    COMPLETE(3, "Complete");

    private final Integer id;
    private final String displayName;
}