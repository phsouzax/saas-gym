package br.com.pedrosouzza.gym_attendance.repository;

import br.com.pedrosouzza.gym_attendance.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);

    Optional<User> findByPassword(String password);
    boolean existsByPassword(String password);

}