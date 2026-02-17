package br.com.pedrosouzza.gym_attendance.service;

import br.com.pedrosouzza.gym_attendance.domain.User;
import br.com.pedrosouzza.gym_attendance.dto.UserDTO;
import br.com.pedrosouzza.gym_attendance.exceptions.BusinessException;
import br.com.pedrosouzza.gym_attendance.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j  // ← ADICIONA ISSO
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserDTO create(UserDTO dto, String password) {
        log.info("Criando novo usuário com email: {}", dto.getEmail());

        if (userRepository.existsByEmail(dto.getEmail())) {
            log.warn("Tentativa de criar usuário com email duplicado: {}", dto.getEmail());
            throw new BusinessException("Email já cadastrado");
        }

        String passwordHash = passwordEncoder.encode(password);

        User user = User.builder()
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .email(dto.getEmail())
                .phone(dto.getPhone())
                .birthDate(dto.getBirthDate())
                .role(dto.getRole())
                .passwordHash(passwordHash)
                .active(true)
                .build();

        user = userRepository.save(user);

        log.info("Usuário criado com sucesso: {} (ID: {})", user.getEmail(), user.getId());

        return toDTO(user);
    }

    public List<UserDTO> findAll() {
        log.info("Listando todos os usuários");

        List<User> users = userRepository.findAll();
        List<UserDTO> result = new ArrayList<>();

        for (User user : users) {
            result.add(toDTO(user));
        }

        log.info("Total de usuários encontrados: {}", result.size());

        return result;
    }

    public UserDTO findById(Long id) {
        log.info("Buscando usuário por ID: {}", id);

        User user = userRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Usuário não encontrado com ID: {}", id);
                    return new BusinessException("Usuário não encontrado");
                });

        log.info("Usuário encontrado: {} (ID: {})", user.getEmail(), user.getId());

        return toDTO(user);
    }

    private UserDTO toDTO(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .birthDate(user.getBirthDate())
                .role(user.getRole())
                .active(user.getActive())
                .build();
    }
}