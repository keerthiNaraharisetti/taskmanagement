package com.taskmanagement.app.request;

import java.sql.Timestamp;
import com.taskmanagement.app.enums.TaskStatus;
import lombok.Data;

@Data
public class TaskRequest {
    private Long id;
    private String title;
    private String description;
    private TaskStatus status;
    private Timestamp dueDate;
    private Timestamp completionDate;
}
