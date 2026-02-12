package br.com.pedrosouzza.gym_attendance.dto;

import lombok.*;

@Getter
@Setter
public class LoginRequestDTO {
    private String email;
    private String password;
}