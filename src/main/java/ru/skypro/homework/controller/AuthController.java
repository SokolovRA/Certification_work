package ru.skypro.homework.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.skypro.homework.dto.LoginReqDTO;
import ru.skypro.homework.dto.RegisterDTO;
import ru.skypro.homework.enums.Role;
import ru.skypro.homework.interfaces.UserAuthInterface;

import static ru.skypro.homework.enums.Role.USER;

@Slf4j
@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequiredArgsConstructor
public class AuthController {
    private final UserAuthInterface authService;
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginReqDTO req) {
        if (authService.login(req.getUserName(), req.getPassword())) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterDTO registerDTO) {
        Role role = registerDTO.getRole() == null ? USER : registerDTO.getRole();
        if (authService.register(registerDTO, role)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
