/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.task_v1.entity;
import com.example.task_v1.TaskStatus;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "tasks")
public class Task {
    @Id
    private String id;
    private String name;
    private String description;
    private Instant createdAt;
    private Instant updatedAt;
    private TaskStatus status;
    private String authorId;
    private String assigneeId;
    private Set<String> observerIds;


    @Transient //не в БД
    private User author;
    @Transient
    private User assignee;
    @Transient
    private Set<User> observers;

    public void setAuthor(User author) {
        this.author = author;
        if (author != null) {
            this.authorId = author.getId();
        }
    }

    public void setAssignee(User assignee) {
        this.assignee = assignee;
        if (assignee != null) {
            this.assigneeId = assignee.getId();
        }
    }

    public void setObservers(Set<User> observers) {
        this.observers = observers;
        if (observers != null && !observers.isEmpty()) {
            this.observerIds = observers.stream().map(User::getId).collect(Collectors.toSet());
        }
    }
}
    

