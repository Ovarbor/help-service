package ru.help.service;

import org.springframework.stereotype.Service;
import ru.help.dto.CreateUserDtoRequest;
import ru.help.dto.UserDtoResponse;
import ru.help.model.User;

import java.util.List;

@Service
public interface UserService {

    UserDtoResponse addUser(CreateUserDtoRequest createUserDtoRequest);

    User findUserByName(String name);

    List<UserDtoResponse> findAllUsers(Integer from, Integer size);

    List<UserDtoResponse> searchUsers(String text, Integer from, Integer size);

    UserDtoResponse assignAuthority(Long userId);
}
