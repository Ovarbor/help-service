package ru.help.model;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "birthday")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate birthday;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "phones", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "phone")
    @AttributeOverrides({
            @AttributeOverride(name = "phone", column = @Column(name = "phone"))
    })
    private List<Phone> phonesList;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "emails", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "email")
    @AttributeOverrides({
            @AttributeOverride(name = "email", column = @Column(name = "email"))
    })
    private List<Email> emailsList;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "authorities", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "authority")
    @AttributeOverrides({
            @AttributeOverride(name = "authority", column = @Column(name = "authority"))
    })
    private Set<Authority> authorityList;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Request> requestList;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User user)) return false;
        return Objects.equals(getUserId(), user.getUserId()) && Objects.equals(getUsername(), user.getUsername()) && Objects.equals(getPassword(), user.getPassword()) && Objects.equals(getBirthday(), user.getBirthday()) && Objects.equals(getPhonesList(), user.getPhonesList()) && Objects.equals(getEmailsList(), user.getEmailsList()) && Objects.equals(getAuthorityList(), user.getAuthorityList()) && Objects.equals(getRequestList(), user.getRequestList());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUserId(), getUsername(), getPassword(), getBirthday(), getPhonesList(), getEmailsList(), getAuthorityList(), getRequestList());
    }
}
