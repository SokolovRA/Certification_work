package ru.skypro.homework.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.RegisterDTO;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService  {

  private final PasswordEncoder encoder;
  private final MyUserDetailsService manager;

  public boolean login(String userName, String password) throws Exception {
    log.info("Used method  is - login");
    UserDetails userDetails = manager.loadUserByUsername(userName);
    if (!encoder.matches(password, userDetails.getPassword())) {
      throw new Exception("Invalid username or password");
    }
    return true;
  }

  public boolean register(RegisterDTO registerDTO) throws Exception {
    if(registerDTO.getUsername() == null || registerDTO.getUsername().isBlank()
            || registerDTO.getFirstName() == null || registerDTO.getFirstName().isBlank()
            || registerDTO.getLastName() == null || registerDTO.getLastName().isBlank()
            || registerDTO.getPhone() == null || registerDTO.getPhone().isBlank()
            || registerDTO.getPassword() == null || registerDTO.getPassword().isBlank()) throw new Exception();
    log.info("Used method  is - register");
    manager.createUser(registerDTO);
    return true;

  }
}
