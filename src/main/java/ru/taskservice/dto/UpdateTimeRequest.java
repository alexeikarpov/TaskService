package ru.taskservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.Duration;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateTimeRequest {
    private Duration duration;
}
