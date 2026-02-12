package br.com.pedrosouzza.gym_attendance.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.format.annotation.DateTimeFormat;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

import br.com.pedrosouzza.gym_attendance.dto.PresenceResponseDTO;
import br.com.pedrosouzza.gym_attendance.service.PresenceService;

@RestController
@RequestMapping ("/api/presence")
@RequiredArgsConstructor
public class PresenceController {

    private final PresenceService presenceService;

    @PostMapping("/register/{userId}")
    public ResponseEntity<PresenceResponseDTO> register(@PathVariable Long userId) {
        PresenceResponseDTO response = presenceService.register(userId);
        return ResponseEntity.ok(response);
    }

@GetMapping ("/user/{userId}")
public ResponseEntity<List<PresenceResponseDTO>> findByUser(
        @PathVariable Long userId,
        @RequestParam @DateTimeFormat (iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
        @RequestParam @DateTimeFormat (iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end
){
List<PresenceResponseDTO> presencas = presenceService.findByUserAndPeriod(userId, start, end);
    return ResponseEntity.ok(presencas);

}}