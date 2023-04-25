package ru.skypro.homework.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.skypro.homework.dto.RegisterDTO;
import ru.skypro.homework.model.User;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

    @Mock
    private PasswordEncoder encoder;

    @Mock
    private MyUserDetailsService manager;

    @InjectMocks
    private AuthService authService;

    @Test
    public void testLogin() {
        String userName = "testUser";
        String password = "testPassword";
        String encodedPassword = "encodedPassword";

        User user = new User();
        user.setUsername(userName);
        user.setPassword(encodedPassword);

        org.springframework.security.core.userdetails.User userDetails =
                new org.springframework.security.core.userdetails.User(
                        user.getUsername(),
                        user.getPassword(),
                        new ArrayList<>()
                );

        when(manager.loadUserByUsername(userName)).thenReturn(userDetails);
        when(encoder.matches(password, encodedPassword)).thenReturn(true);

        boolean result = authService.login(userName, password);

        assertTrue(result);
        verify(manager, times(1)).loadUserByUsername(userName);
        verify(encoder, times(1)).matches(password, encodedPassword);
    }
    @Test
    public void testRegister() throws Exception {
        RegisterDTO registerDTO = new RegisterDTO();
        registerDTO.setFirstName("Roman");
        registerDTO.setLastName("Sokolov");
        registerDTO.setPhone("+79214403109");
        registerDTO.setUsername("romansokol");
        registerDTO.setPassword("password");

        doNothing().when(manager).createUser(registerDTO);

        boolean result = authService.register(registerDTO);

        assertTrue(result);
        verify(manager).createUser(registerDTO);
    }
}


