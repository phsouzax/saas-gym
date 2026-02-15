package br.com.pedrosouzza.gym_attendance.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PresenceResponseDTO {
    private Long id;
    private Long userId;
    private String name;
    private LocalDateTime timestamp;
}