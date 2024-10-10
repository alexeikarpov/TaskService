package ru.taskservice.dto;

import lombok.Getter;
import lombok.Setter;
import ru.taskservice.enums.DefaultStatus;

@Getter
@Setter
public class UpdateStatusRequest {
    DefaultStatus newStatus;

    public UpdateStatusRequest() {}

    public UpdateStatusRequest(DefaultStatus newStatus) {
        this.newStatus = newStatus;
    }
}
