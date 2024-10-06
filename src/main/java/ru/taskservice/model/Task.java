package ru.taskservice.model;

import lombok.Getter;
import lombok.Setter;
import java.time.Duration;
import java.util.UUID;

@Getter
@Setter
public class Task {
    private UUID id;
    private String name;
    private String description;
    private Duration timeToComplete;

    public Task() {}

    public Task(UUID id, String name, String description, Duration timeToComplete) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.description = description;
        this.timeToComplete = timeToComplete;
    }
}
