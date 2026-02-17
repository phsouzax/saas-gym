package br.com.pedrosouzza.gym_attendance.service;

import br.com.pedrosouzza.gym_attendance.domain.User;
import br.com.pedrosouzza.gym_attendance.dto.LoginRequestDTO;
import br.com.pedrosouzza.gym_attendance.dto.LoginResponseDTO;
import br.com.pedrosouzza.gym_attendance.exceptions.BusinessException;
import br.com.pedrosouzza.gym_attendance.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j  // ← ADICIONA ISSO
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public LoginResponseDTO login(LoginRequestDTO requestDTO) {
        log.info("Tentativa de login para email: {}", requestDTO.getEmail());

        User user = userRepository.findByEmail(requestDTO.getEmail())
                .orElseThrow(() -> {
                    log.warn("Tentativa de login com email inexistente: {}", requestDTO.getEmail());
                    return new BusinessException("Email ou senha inválidos");
                });

        if (!passwordEncoder.matches(requestDTO.getPassword(), user.getPasswordHash())) {
            log.warn("Tentativa de login com senha incorreta para email: {}", requestDTO.getEmail());
            throw new BusinessException("Email ou senha inválidos");
        }

        if (!user.getActive()) {
            log.warn("Tentativa de login de usuário inativo: {}", requestDTO.getEmail());
            throw new BusinessException("Acesso negado");
        }

        log.info("Login bem-sucedido para usuário: {} (ID: {})", user.getEmail(), user.getId());

        return LoginResponseDTO.builder()
                .userId(user.getId())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .role(user.getRole())
                .build();
    }
}