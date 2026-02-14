package br.com.pedrosouzza.gym_attendance.dto;

import br.com.pedrosouzza.gym_attendance.domain.Role;

import java.time.LocalDate;

public class CreateUserRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private LocalDate birthDate;
    private Role role;
    private String password;
}
