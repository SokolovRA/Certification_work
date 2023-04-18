package ru.skypro.homework.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.RegisterDTO;
import ru.skypro.homework.enums.Role;
import ru.skypro.homework.interfaces.UserAuthInterface;


@Service
@RequiredArgsConstructor
public class AuthService implements UserAuthInterface {

  private final UserDetailsManager manager;

  private final PasswordEncoder encoder;


  @Override
  public boolean login(String userName, String password) {
    if (!manager.userExists(userName)) {
      return false;
    }
    UserDetails userDetails = manager.loadUserByUsername(userName);
    return encoder.matches(password, userDetails.getPassword());
  }

  @Override
  public boolean register(RegisterDTO registerDTO, Role role) {
    if (manager.userExists(registerDTO.getUserName())) {
      return false;
    }
    manager.createUser(
        User.builder()
            .passwordEncoder(this.encoder::encode)
            .password(registerDTO.getPassword())
            .username(registerDTO.getUserName())
            .roles(role.name())
            .build());
    return true;
  }
}
