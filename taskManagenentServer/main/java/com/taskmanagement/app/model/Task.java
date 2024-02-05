package com.taskmanagement.app.model;

import java.sql.Timestamp;
import com.taskmanagement.app.enums.TaskStatus;
import lombok.Data;
import javax.persistence.*;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

@Entity
@Table(name = "tasks")
@Data
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;
    
    @Column(name = "description")
    private String description;
    
    @Column(name = "status")
    @Enumerated
    private TaskStatus status;

    @Column(name = "due_date")
    private Timestamp dueDate;

    @Column(name = "completion_date")
    private Timestamp completionDate;

    @Column(name = "created_by")
    private Long createdBy;

    @Column(name = "updated_by")
    private Long updatedBy;

    @ManyToOne
    @JoinColumn(name = "created_by", referencedColumnName = "id", insertable = false, updatable = false)
    @NotFound(action = NotFoundAction.IGNORE)
    private User createdByUser;

    @ManyToOne
    @JoinColumn(name = "updated_by", referencedColumnName = "id", insertable = false, updatable = false)
    @NotFound(action = NotFoundAction.IGNORE)
    private User updatedByUser;
}