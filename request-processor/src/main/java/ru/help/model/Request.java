package ru.help.model;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

@Entity
@Table(name = "requests")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = {"user"})
public class Request {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "request_id")
    private Long requestId;

    @Column(name = "text")
    private String text;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private RequestStatus requestStatus = RequestStatus.DRAFT;

    @Column(name = "created")
    @Builder.Default
    private LocalDateTime created = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);

    @Column(name = "processed")
    private LocalDateTime processed;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Request request)) return false;
        return Objects.equals(getRequestId(), request.getRequestId()) && Objects.equals(getText(), request.getText()) && getRequestStatus() == request.getRequestStatus() && Objects.equals(getCreated(), request.getCreated()) && Objects.equals(getProcessed(), request.getProcessed()) && Objects.equals(getUser(), request.getUser());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getRequestId(), getText(), getRequestStatus(), getCreated(), getProcessed(), getUser());
    }
}
