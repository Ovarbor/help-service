package ru.help.mapper;

import org.mapstruct.Mapper;
import ru.help.dto.UserDtoResponse;
import ru.help.model.User;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDtoResponse toUserDtoResponse(User user);

    List<UserDtoResponse> toUserDtoResponseList(List<User> userList);
}
