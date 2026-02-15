package br.com.pedrosouzza.gym_attendance.service;

import br.com.pedrosouzza.gym_attendance.domain.User;
import br.com.pedrosouzza.gym_attendance.dto.LoginRequestDTO;
import br.com.pedrosouzza.gym_attendance.dto.LoginResponseDTO;
import br.com.pedrosouzza.gym_attendance.exceptions.BusinessException;
import br.com.pedrosouzza.gym_attendance.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public LoginResponseDTO login(LoginRequestDTO requestDTO) {
         User user = userRepository.findByEmail(requestDTO.getEmail())
                 .orElseThrow(() -> new BusinessException("Email ou senha inválidos"));

         if (!passwordEncoder.matches(requestDTO.getPassword(), user.getPasswordHash())) {
             throw new RuntimeException("Email ou senha inválidos");
         }

    if (!user.getActive()) {
        throw new RuntimeException("Acesso negado");
    }
            return LoginResponseDTO.builder()
                    .userId(user.getId())
                    .email(user.getEmail())
                    .firstName(user.getFirstName())
                    .lastName(user.getLastName())
                    .role(user.getRole())
                    .build();
        }
    }
