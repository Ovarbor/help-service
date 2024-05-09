package ru.help.repo;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.help.model.Request;

import java.util.List;
import java.util.Optional;

@Repository
public interface RequestRepo extends JpaRepository<Request, Long> {

    @Query(value = "SELECT r FROM Request r where r.user.userId = :userId and r.requestId = :requestId " +
            "and r.requestStatus = 'DRAFT'")
    Optional<Request> findByIdAndUserIdAndRequestStatusDraft(@Param("userId")Long userId, @Param("requestId")Long requestId);

    @Query(value = "SELECT r FROM Request r where r.user.userId = :userId")
    List<Request> findAllByUserId(@Param("userId")Long userId, Pageable pageable);
    
    @Query(value = "SELECT r FROM Request r where r.requestId = :requestId AND r.requestStatus = 'SEND'")
    Optional<Request> findByRequestIdAndRequestStatusSend(@Param("requestId")Long requestId);

    @Query(value = "SELECT r FROM Request r where r.requestStatus = 'SEND'")
    List<Request> findAllByRequestStatusSend(Pageable pageable);

    @Query(value = "SELECT r FROM Request r where r.user.username LIKE %:username% AND r.requestStatus = 'SEND'")
    List<Request> findAllRequestsByUsernameAndRequestStatusSend(@Param("username")String username, Pageable pageable);
}
