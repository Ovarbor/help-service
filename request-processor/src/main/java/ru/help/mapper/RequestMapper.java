package ru.help.mapper;

import org.mapstruct.Mapper;
import ru.help.dto.OperatorRequestDtoResponse;
import ru.help.dto.UserRequestDtoResponse;
import ru.help.model.Request;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RequestMapper {

    UserRequestDtoResponse toUserRequestDtoResponse(Request request);

    OperatorRequestDtoResponse toOperatorRequestDtoResponse(Request request);

    List<UserRequestDtoResponse> toUserRequestDtoResponseList(List<Request> requestList);

    List<OperatorRequestDtoResponse> toOperatorRequestDtoResponseList(List<Request> requestList);
}
