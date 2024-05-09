package ru.help.service;

import org.springframework.stereotype.Service;
import ru.help.dto.OperatorRequestDtoResponse;
import ru.help.dto.RequestDtoRequest;
import ru.help.dto.UserRequestDtoResponse;

import java.util.List;

@Service
public interface RequestService {

    UserRequestDtoResponse createRequest(Long userId, RequestDtoRequest requestDtoRequest);

    UserRequestDtoResponse updateRequest(Long userId, Long requestId, RequestDtoRequest requestDtoRequest);

    UserRequestDtoResponse sendRequest(Long userId, Long requestId);

    List<UserRequestDtoResponse> getRequests(Long userId, Integer from, Integer size, String sortType);

    OperatorRequestDtoResponse acceptRequest(Long requestId);

    OperatorRequestDtoResponse deniedRequest(Long requestId);

    List<OperatorRequestDtoResponse> getAllRequests(Integer from, Integer size, String sortType);

    List<OperatorRequestDtoResponse> getAllUserRequests(String name, Integer from, Integer size, String sortType);
}
