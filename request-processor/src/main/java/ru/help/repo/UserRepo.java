package ru.help.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
import ru.help.model.Email;
import ru.help.model.Phone;
import ru.help.model.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Long>, QuerydslPredicateExecutor<User> {

    Optional<User> findByUsername(String username);

    @Query(value = "SELECT u.username FROM User u")
    List<String> findAllUsernames();

    @Query(value = "SELECT u.emailsList FROM User u")
    List<Email> findAllEmails();

    @Query(value = "SELECT u.phonesList FROM User u")
    List<Phone> findAllPhones();
}
