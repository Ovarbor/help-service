package ru.help.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import ru.help.model.Authority;
import ru.help.model.Email;
import ru.help.model.Phone;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserDtoResponse {

    private Long userId;

    private String username;

    private LocalDate birthday;

    private List<Email> emailsList;

    private List<Phone> phonesList;

    private Set<Authority> authorityList;
}
