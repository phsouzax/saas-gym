package br.com.pedrosouzza.gym_attendance.service;

import br.com.pedrosouzza.gym_attendance.domain.PresenceEvent;
import br.com.pedrosouzza.gym_attendance.domain.User;
import br.com.pedrosouzza.gym_attendance.dto.PresenceResponseDTO;
import br.com.pedrosouzza.gym_attendance.exceptions.BusinessException;
import br.com.pedrosouzza.gym_attendance.repository.PresenceRepository;
import br.com.pedrosouzza.gym_attendance.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PresenceService {

    private final PresenceRepository presenceRepository;
    private final UserRepository userRepository;

    public PresenceResponseDTO register(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException("Usuário não encontrado"));

        PresenceEvent event = PresenceEvent.builder()
                .user(user)
                .timestamp(LocalDateTime.now())
                .build();

        event = presenceRepository.save(event);

        return toDTO(event);
    }

    public List<PresenceResponseDTO> findByUserAndPeriod(Long userId, LocalDateTime start, LocalDateTime end) {
        List<PresenceEvent> events = presenceRepository.findByUserIdAndTimestampBetween(userId, start, end);
        List<PresenceResponseDTO> result = new ArrayList<>();

        for (PresenceEvent event : events) {
            result.add(toDTO(event));
        }

        return result;
    }

    private PresenceResponseDTO toDTO(PresenceEvent event) {
        return PresenceResponseDTO.builder()
                .id(event.getId())
                .userId(event.getUser().getId())
                .name(event.getUser().getFirstName() + " " + event.getUser().getLastName())
                .timestamp(event.getTimestamp())
                .build();
    }
}