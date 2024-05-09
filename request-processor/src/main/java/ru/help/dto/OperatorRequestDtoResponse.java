package ru.help.dto;


import lombok.*;
import ru.help.model.RequestStatus;

import java.time.LocalDateTime;
import java.util.stream.IntStream;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OperatorRequestDtoResponse {

    @Setter
    private Long requestId;

    private String text;

    @Setter
    private RequestStatus requestStatus;

    @Setter
    private LocalDateTime created;

    @Setter
    private LocalDateTime processed;

    public void setText(String text) {
        this.text = stringConversion(text);
    }

    private String stringConversion(String input) {
        StringBuilder stringBuilder = new StringBuilder();
        String[] splitted = input.split("");
        IntStream.range(0, splitted.length).forEach(i -> {
            if (i == splitted.length - 1) {
                stringBuilder.append(splitted[i]);
            } else {
                stringBuilder.append(splitted[i]).append("-");
            }
        });
        return stringBuilder.toString().replace(" ", "");
    }
}
