package br.com.pedrosouzza.gym_attendance.dto;

import br.com.pedrosouzza.gym_attendance.domain.Role;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDate;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {
    private Long id;

    @NotBlank(message = "Nome é obrigatório")
    @Size(min = 2, max = 50, message = "Nome deve ter entre 2 e 50 caracteres")
    private String firstName;

    @NotBlank(message = "Sobrenome é obrigatório")
    @Size(min = 2, max = 50, message = "Sobrenome deve ter entre 2 e 50 caracteres")
    private String lastName;

    @NotBlank(message = "Email é obrigatório")
    @Email(message = "Email inválido")
    @Size (max = 100, message = "Email deve ter no máximo 100 caracteres")
    private String email;

    @Size(max = 15, message = "Telefone deve ter no máximo 15 caracteres")
    private String phone;

    @Past(message = "Data de nascimento deve ser no passado")
    private LocalDate birthDate;

    @NotNull(message = "Perfil é obrigatório")
    private Role role;

    private Boolean active;
}