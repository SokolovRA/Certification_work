package ru.skypro.homework.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import ru.skypro.homework.dto.LoginDTO;
import ru.skypro.homework.dto.RegisterDTO;
import ru.skypro.homework.enums.Role;
import ru.skypro.homework.model.User;
import ru.skypro.homework.repository.UserRepository;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private ObjectMapper objectMapper;

    private final LoginDTO loginDTO = new LoginDTO();
    private final User user = new User();
    private final RegisterDTO registerDTO = new RegisterDTO();


    @BeforeEach
    void setUp() {
            user.setUsername("sokolov@bk.ru");
            user.setFirstName("Roman");
            user.setLastName("Sokolov");
            user.setPhone("+79214403109");
            user.setPassword(passwordEncoder.encode("password"));
            user.setRole(Role.USER);
            user.setEnabled(true);
            userRepository.save(user);

            loginDTO.setUsername("sokolov@bk.ru");
            loginDTO.setPassword("password");

            registerDTO.setUsername("sokolov2@bk.ru");
            registerDTO.setFirstName("Roman2");
            registerDTO.setLastName("Sokolov2");
            registerDTO.setPhone("+79217778707");
            registerDTO.setPassword("password2");
            registerDTO.setRole(Role.ADMIN);
    }

    @AfterEach
    void clearAll() {
        userRepository.delete(user);
    }

    @Test
    public void testLoginSuccessReturnsValidCredentialsWhenLoginSucceeds() throws Exception {
        mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginDTO)))
                .andExpect(status().isOk());
    }


    @Test
    public void testRegisterReturnsValidCredentialsWhenRegisterSuccess() throws Exception {
        mockMvc.perform(post("/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerDTO)))
                .andExpect(status().isOk());

        User savedUser = userRepository.findByUsernameIgnoreCase(registerDTO.getUsername()).orElseThrow(Exception::new);
        userRepository.delete(savedUser);
    }
}