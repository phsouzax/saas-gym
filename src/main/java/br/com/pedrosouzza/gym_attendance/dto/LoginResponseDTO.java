package br.com.pedrosouzza.gym_attendance.dto;

import br.com.pedrosouzza.gym_attendance.domain.Role;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginResponseDTO {
    private Long id;
    private String email;
    private String name;
    private String lastName;
    private Role role;
    private String token;

}
