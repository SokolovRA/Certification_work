package ru.skypro.homework.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.RegisterDTO;
import ru.skypro.homework.security.MyUserDetailsService;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService  {

  private final PasswordEncoder encoder;
  private final MyUserDetailsService manager;

  public boolean login(String userName, String password) {
    UserDetails userDetails = manager.loadUserByUsername(userName);
    return encoder.matches(password, userDetails.getPassword());
  }

  public boolean register(RegisterDTO registerDTO) throws Exception {
    manager.createUser(registerDTO);
    return true;

  }
}
