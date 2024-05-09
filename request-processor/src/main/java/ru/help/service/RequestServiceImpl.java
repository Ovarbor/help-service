package ru.help.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.help.dto.OperatorRequestDtoResponse;
import ru.help.dto.RequestDtoRequest;
import ru.help.dto.UserRequestDtoResponse;
import ru.help.exception.NotFoundValidationException;
import ru.help.mapper.RequestMapper;
import ru.help.model.Request;
import ru.help.model.RequestStatus;
import ru.help.model.User;
import ru.help.repo.RequestRepo;
import ru.help.repo.UserRepo;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RequestServiceImpl implements RequestService {

    private final UserRepo userRepository;
    private final RequestRepo requestRepository;
    private final RequestMapper requestMapper;

    @Override
    public UserRequestDtoResponse createRequest(Long userId, RequestDtoRequest requestDtoRequest) {
        User user = userRepository.findById(userId).orElseThrow(() ->
                new NotFoundValidationException("User with id: " + userId + " not found"));
        Request request = new Request();
        request.setText(requestDtoRequest.getText());
        request.setUser(user);
        requestRepository.save(request);
        return requestMapper.toUserRequestDtoResponse(request);
    }

    @Override
    public UserRequestDtoResponse updateRequest(Long userId, Long requestId, RequestDtoRequest requestDtoRequest) {
        Request request = requestRepository.findByIdAndUserIdAndRequestStatusDraft(userId, requestId).orElseThrow(() ->
                new NotFoundValidationException("Request with id: " + requestId + " for user with id: " + userId +
                        " and status draft not found"));
        request.setText(requestDtoRequest.getText());
        requestRepository.save(request);
        return requestMapper.toUserRequestDtoResponse(request);
    }

    @Override
    public UserRequestDtoResponse sendRequest(Long userId, Long requestId) {
        Request request = requestRepository.findByIdAndUserIdAndRequestStatusDraft(userId, requestId).orElseThrow(() ->
                new NotFoundValidationException("Request with id: " + requestId + " for user with id: " + userId +
                        " and status draft not found"));
        request.setRequestStatus(RequestStatus.SEND);
        requestRepository.save(request);
        return requestMapper.toUserRequestDtoResponse(request);
    }

    @Override
    public List<UserRequestDtoResponse> getRequests(Long userId, Integer from, Integer size, String sortType) {
        List<Request> requestList = new ArrayList<>();
        if (sortType.equals("asc")) {
            requestList = requestRepository.findAllByUserId(userId, PageRequest.of(from, size,
                    Sort.by("created").ascending()));
        } else if (sortType.equals("desc")) {
            requestList = requestRepository.findAllByUserId(userId, PageRequest.of(from, size,
                    Sort.by("created").descending()));
        }
        return requestMapper.toUserRequestDtoResponseList(requestList);
    }

    @Override
    public OperatorRequestDtoResponse acceptRequest(Long requestId) {
        Request request = requestRepository.findByRequestIdAndRequestStatusSend(requestId).orElseThrow(() ->
                new NotFoundValidationException("Request with id: " + requestId + " and status SEND not found"));
        request.setRequestStatus(RequestStatus.ACCEPTED);
        request.setProcessed(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        requestRepository.save(request);
        return requestMapper.toOperatorRequestDtoResponse(request);
    }

    @Override
    public OperatorRequestDtoResponse deniedRequest(Long requestId) {
        Request request = requestRepository.findByRequestIdAndRequestStatusSend(requestId).orElseThrow(() ->
                new NotFoundValidationException("Request with id: " + requestId + " and status SEND not found"));
        request.setRequestStatus(RequestStatus.DENIED);
        request.setProcessed(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        requestRepository.save(request);
        return requestMapper.toOperatorRequestDtoResponse(request);
    }

    @Override
    public List<OperatorRequestDtoResponse> getAllRequests(Integer from, Integer size, String sortType) {
        List<Request> requestList = new ArrayList<>();
        if (sortType.equals("asc")) {
            requestList = requestRepository.findAllByRequestStatusSend(PageRequest.of(from, size,
                    Sort.by("created").ascending()));
        } else if (sortType.equals("desc")) {
            requestList = requestRepository.findAllByRequestStatusSend(PageRequest.of(from, size,
                    Sort.by("created").descending()));
        }
        return requestMapper.toOperatorRequestDtoResponseList(requestList);
    }

    @Override
    public List<OperatorRequestDtoResponse> getAllUserRequests(String username, Integer from, Integer size, String sortType) {
        List<Request> requestList = new ArrayList<>();
        if (sortType.equals("asc")) {
            requestList = requestRepository.findAllRequestsByUsernameAndRequestStatusSend(username, PageRequest.of(from, size,
                    Sort.by("created").ascending()));
        } else if (sortType.equals("desc")) {
            requestList = requestRepository.findAllRequestsByUsernameAndRequestStatusSend(username, PageRequest.of(from, size,
                    Sort.by("created").descending()));
        }
        return requestMapper.toOperatorRequestDtoResponseList(requestList);
    }
}
