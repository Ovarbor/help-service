package ru.help.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import ru.help.model.RequestStatus;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserRequestDtoResponse {

    private Long requestId;

    private String text;

    private RequestStatus requestStatus;

    private LocalDateTime created;

    private LocalDateTime processed;
}
