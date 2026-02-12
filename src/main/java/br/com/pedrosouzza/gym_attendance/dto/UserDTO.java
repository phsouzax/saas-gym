package br.com.pedrosouzza.gym_attendance.dto;

import br.com.pedrosouzza.gym_attendance.domain.Role;
import lombok.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private LocalDate birthDate;
    private Role role;
    private Boolean active;

}