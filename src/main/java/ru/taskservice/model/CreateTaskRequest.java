package ru.taskservice.model;

import lombok.Getter;
import lombok.Setter;
import java.time.Duration;

@Getter
@Setter
public class CreateTaskRequest {
    private String name;
    private String description;
    private DefaultStatus defaultStatus;
    private Duration timeToComplete;

    public CreateTaskRequest() {}
}

