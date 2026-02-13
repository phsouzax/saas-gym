package br.com.pedrosouzza.gym_attendance.service;

import br.com.pedrosouzza.gym_attendance.dto.*;
import br.com.pedrosouzza.gym_attendance.domain.User;
import br.com.pedrosouzza.gym_attendance.dto.UserDTO;
import br.com.pedrosouzza.gym_attendance.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserDTO createUser(UserDTO userDTO, String password) {
         if (userRepository.findByEmail(userDTO.getEmail())) {
             throw new RuntimeException("Email já cadastrado");

             String passwordHash = passwordEncoder.encode(password);

             User user = User.builder()
             .lastName(user.getLastName())
             .email(user.getEmail())
             .phone(user.getPhone())
             .birthDate(user.getBirthDate())
             .role(user.getRole())
             .passwordHash(passwordHash)
             .active(true)
             .build();
         }
    }
}
