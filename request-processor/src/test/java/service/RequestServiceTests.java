package service;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import ru.help.RequestProcessor;
import ru.help.dto.*;
import ru.help.service.RequestService;
import ru.help.service.UserService;
import java.time.LocalDate;
import java.util.List;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest(classes = {RequestProcessor.class})
@Transactional
@AutoConfigureMockMvc
public class RequestServiceTests {

    @Autowired
    private RequestService requestService;
    @Autowired
    private UserService userService;

    private CreateUserDtoRequest createUserOneDtoRequest;
    private CreateUserDtoRequest createUserTwoDtoRequest;
    private RequestDtoRequest requestDtoOneRequest;
    private RequestDtoRequest requestDtoTwoRequest;
    private RequestDtoRequest requestDtoThreeRequest;


    @BeforeEach
    void setUp() {
        createUserOneDtoRequest = new CreateUserDtoRequest("userOne", "passwordOne",
                "89990987873", "userOne@mail.ru", LocalDate.of(1990, 1, 1));
        createUserTwoDtoRequest = new CreateUserDtoRequest("userTwo", "passwordTwo",
                "89140494943", "userTwo@mail.ru", LocalDate.of(2000, 11, 17));
        requestDtoOneRequest = new RequestDtoRequest("Мне нужна помощь");
        requestDtoTwoRequest = new RequestDtoRequest("Мне не нужна помощь");
        requestDtoThreeRequest = new RequestDtoRequest("Я хотел бы подать жалобу.");
    }

    @Test
    void shouldReturnModifiedStringWhenOk() {
      UserDtoResponse u1 = userService.addUser(createUserOneDtoRequest);
      UserDtoResponse u2 = userService.addUser(createUserTwoDtoRequest);
      UserRequestDtoResponse r1 = requestService.createRequest(u1.getUserId(), requestDtoOneRequest);
      UserRequestDtoResponse r2 = requestService.createRequest(u1.getUserId(), requestDtoThreeRequest);
      requestService.sendRequest(u1.getUserId(), r1.getRequestId());
      requestService.sendRequest(u1.getUserId(), r2.getRequestId());
      userService.assignAuthority(u2.getUserId());
      List<OperatorRequestDtoResponse> operatorRequestDtoResponseList =
              requestService.getAllUserRequests("userOne", 0, 5, "asc");
        System.out.println(operatorRequestDtoResponseList);
      assertThat(operatorRequestDtoResponseList.get(0).getText(),
              equalTo("М-н-е--н-у-ж-н-а--п-о-м-о-щ-ь"));
      assertThat(operatorRequestDtoResponseList.get(1).getText(),
              equalTo("Я--х-о-т-е-л--б-ы--п-о-д-а-т-ь--ж-а-л-о-б-у-."));
    }
}
