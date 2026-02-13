package br.com.pedrosouzza.gym_attendance.dto;

import br.com.pedrosouzza.gym_attendance.domain.Role;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginResponseDTO {
    private Long userId;
    private String email;
    private String name;
    private String firstName;
    private String lastName;
    private Role role;
    private String token;

    public void setUserId(Long id) {
    }

    public void setFirstName(String firstName) {

    }
}
