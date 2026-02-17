package br.com.pedrosouzza.gym_attendance.service;

import br.com.pedrosouzza.gym_attendance.domain.PresenceEvent;
import br.com.pedrosouzza.gym_attendance.domain.User;
import br.com.pedrosouzza.gym_attendance.dto.PresenceResponseDTO;
import br.com.pedrosouzza.gym_attendance.exceptions.BusinessException;
import br.com.pedrosouzza.gym_attendance.repository.PresenceRepository;
import br.com.pedrosouzza.gym_attendance.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j  // ← ADICIONA ISSO
@Service
@RequiredArgsConstructor
public class PresenceService {

    private final PresenceRepository presenceRepository;
    private final UserRepository userRepository;

    public PresenceResponseDTO register(Long userId) {
        log.info("Registrando presença para usuário ID: {}", userId);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> {
                    log.warn("Tentativa de registrar presença para usuário inexistente ID: {}", userId);
                    return new BusinessException("Usuário não encontrado");
                });

        PresenceEvent event = PresenceEvent.builder()
                .user(user)
                .timestamp(LocalDateTime.now())
                .build();

        event = presenceRepository.save(event);

        log.info("Presença registrada: {} {} (ID: {}) às {}",
                user.getFirstName(), user.getLastName(), user.getId(), event.getTimestamp());

        return toDTO(event);
    }

    public List<PresenceResponseDTO> findByUserAndPeriod(Long userId, LocalDateTime start, LocalDateTime end) {
        log.info("Consultando presenças do usuário ID: {} no período de {} até {}", userId, start, end);

        List<PresenceEvent> events = presenceRepository.findByUserIdAndTimestampBetween(userId, start, end);
        List<PresenceResponseDTO> result = new ArrayList<>();

        for (PresenceEvent event : events) {
            result.add(toDTO(event));
        }

        log.info("Total de presenças encontradas: {}", result.size());

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