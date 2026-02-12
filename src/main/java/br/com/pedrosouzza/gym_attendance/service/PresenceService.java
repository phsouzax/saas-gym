package br.com.pedrosouzza.gym_attendance.service;

import br.com.pedrosouzza.gym_attendance.domain.User;
import br.com.pedrosouzza.gym_attendance.domain.PresenceEvent;
import br.com.pedrosouzza.gym_attendance.dto.PresenceResponseDTO;
import br.com.pedrosouzza.gym_attendance.repository.PresenceRepository;
import br.com.pedrosouzza.gym_attendance.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.data.projection.EntityProjection.ProjectionType.DTO;

@Service
@RequiredArgsConstructor
public class PresenceService {
    private final PresenceRepository presenceRepository;
    private final UserRepository userRepository;

    public PresenceResponseDTO register(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        PresenceEvent event = new PresenceEvent();
        event.setUser(user);
        event.setTimestamp(LocalDateTime.now());

        presenceRepository.save(event);

        PresenceResponseDTO responseDTO = new PresenceResponseDTO();
        responseDTO.setId(event.getId());
        responseDTO.setUserId(user.getId());
        responseDTO.setUserName(user.getFirstName() + " " + user.getLastName());
        responseDTO.setTimestamp(LocalDateTime.now());
        return responseDTO;
    }

    public List<PresenceResponseDTO> findByUserAndPeriod(Long userId,
                                                         LocalDateTime start, LocalDateTime end) {
        List<PresenceEvent> events = presenceRepository.findByUserIdAndTimestampBetween(userId, start, end);

        List<PresenceResponseDTO> result = new ArrayList<PresenceResponseDTO>();
        for (PresenceEvent event : events) {
            PresenceResponseDTO responseDTO = new PresenceResponseDTO();
            responseDTO.setId(event.getId());
            responseDTO.setUserId(event.getUser().getId());
            responseDTO.setUserName(event.getUser().getFirstName() + " " + event.getUser().getLastName());
            responseDTO.setTimestamp(event.getTimestamp());

            result.add(responseDTO);
        }
        return result;
    }
}
