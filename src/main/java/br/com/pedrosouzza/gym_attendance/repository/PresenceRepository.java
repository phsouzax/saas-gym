package br.com.pedrosouzza.gym_attendance.repository;

import br.com.pedrosouzza.gym_attendance.domain.PresenceEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PresenceRepository extends JpaRepository<PresenceEvent, Long> {

    List<PresenceEvent> findByUserIdAndTimestampBetween(Long userId, LocalDateTime start, LocalDateTime end);
}
