package ru.help.service;

import com.querydsl.core.types.dsl.BooleanExpression;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.help.dto.CreateUserDtoRequest;
import ru.help.dto.UserDtoResponse;
import ru.help.exception.ConflictException;
import ru.help.exception.NotFoundValidationException;
import ru.help.mapper.UserMapper;
import ru.help.model.*;
import ru.help.repo.UserRepo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepo userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    private void postConstruct() {
        User user = new User();
        user.setBirthday(LocalDate.of(0, 1, 1));
        user.setUsername("admin");
        user.setPassword(passwordEncoder.encode("admin"));
        user.setAuthorityList(new HashSet<>());
        user.setEmailsList(new ArrayList<>());
        user.setPhonesList(new ArrayList<>());
        user.getAuthorityList().add(new Authority(EnumAuth.ADMIN));
        user.getEmailsList().add(new Email("admin@mail.ru"));
        user.getPhonesList().add(new Phone("891382828282"));
        userRepository.save(user);
    }

    @Override
    public UserDtoResponse addUser(CreateUserDtoRequest createUserDtoRequest) {
        Phone phone = new Phone(createUserDtoRequest.getPhone());
        Email email = new Email(createUserDtoRequest.getEmail());
        usernameValidation(createUserDtoRequest.getUsername());
        emailValidation(email);
        phoneValidation(phone);
        User user = new User();
        user.setBirthday(createUserDtoRequest.getBirthday());
        user.setUsername(createUserDtoRequest.getUsername());
        user.setPassword(passwordEncoder.encode(createUserDtoRequest.getPassword()));
        user.setPhonesList(new ArrayList<>());
        user.getPhonesList().add(phone);
        user.setEmailsList(new ArrayList<>());
        user.getEmailsList().add(email);
        user.setAuthorityList(new HashSet<>());
        user.getAuthorityList().add(new Authority(EnumAuth.USER));
        userRepository.save(user);
        return userMapper.toUserDtoResponse(user);
    }


    @Override
    public User findUserByName(String username) {
        return userRepository.findByUsername(username).orElseThrow(() ->
                new NotFoundValidationException("User with username: " + username + "not found"));
    }

    @Override
    public List<UserDtoResponse> findAllUsers(Integer from, Integer size) {
        List<User> userList = userRepository.findAll(PageRequest.of(from, size,
                Sort.by("username").ascending())).toList();
        return userMapper.toUserDtoResponseList(userList);
    }

    @Override
    public List<UserDtoResponse> searchUsers(String text, Integer from, Integer size) {
        Pageable page = PageRequest.of(from, size, Sort.by("username").ascending());
        BooleanExpression expression = buildExpression(text);
        List<User> userList = userRepository.findAll(expression, page).getContent();
        return userMapper.toUserDtoResponseList(userList);
    }

    @Override
    public UserDtoResponse assignAuthority(Long userId) {
        User targetUser = userRepository.findById(userId).orElseThrow(() ->
                new NotFoundValidationException("User with id: " + userId + " not found"));
        targetUser.getAuthorityList().add(new Authority(EnumAuth.OPERATOR));
        userRepository.save(targetUser);
        return userMapper.toUserDtoResponse(targetUser);
    }

    private BooleanExpression buildExpression (String text) {
        QUser qUser = QUser.user;
        BooleanExpression expression = qUser.eq(qUser);
        if (text != null) {
            expression = expression.and(qUser.username.containsIgnoreCase(text));
        }
        return expression;
    }

    private void usernameValidation(String username) {
        Set<String> userNamesSet = new HashSet<>(userRepository.findAllUsernames());
        if (userNamesSet.contains(username)) {
            throw new ConflictException("Username: " + username + ", already used");
        }
    }

    private void emailValidation(Email email) {
        Set<Email> emailsSet = new HashSet<>(userRepository.findAllEmails());
        if (emailsSet.contains(email)) {
            throw new ConflictException("Email: " + email.getEmail() + ", already used");
        }
    }

    private void phoneValidation(Phone phone) {
        Set<Phone> phonesSet = new HashSet<>(userRepository.findAllPhones());
        if (phonesSet.contains(phone)) {
            throw new ConflictException("Phone: " + phone.getPhone() + ", already used");
        }
    }
}
