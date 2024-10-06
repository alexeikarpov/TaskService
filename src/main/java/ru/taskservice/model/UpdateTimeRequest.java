package ru.taskservice.model;

import lombok.Getter;
import lombok.Setter;
import java.time.Duration;
import java.util.UUID;

@Getter
@Setter
public class UpdateTimeRequest {
    private UUID id;
    private Duration duration;

    public UpdateTimeRequest() {}
}
