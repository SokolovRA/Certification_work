package ru.skypro.homework.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.PasswordDTO;
import ru.skypro.homework.dto.UserDTO;
import ru.skypro.homework.mapper.UserMapper;
import ru.skypro.homework.model.Image;
import ru.skypro.homework.model.User;
import ru.skypro.homework.repository.UserRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.ExpectedCount.times;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserService userService;

    @Test
    public void testGetAuthorizedUsers() {
        String username = "testuser";
        User user = new User();
        user.setUsername(username);
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(username);

        when(userRepository.findByUsernameIgnoreCase(username)).thenReturn(Optional.of(user));
        when(userMapper.toDto(user)).thenReturn(userDTO);

        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn(username);

        UserDTO result = userService.getAuthorizedUsers(authentication);

        assertEquals(userDTO, result);
        Mockito.verify(userRepository).findByUsernameIgnoreCase(username);
        Mockito.verify(userMapper).toDto(user);
    }

    @Test
    public void testUpdateUser() throws Exception {
        String username = "testuser";
        User user = new User();
        user.setUsername(username);
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(username);
        userDTO.setFirstName("Roman");
        userDTO.setLastName("Sokolov");
        userDTO.setPhone("123456789");

        when(userRepository.findByUsernameIgnoreCase(username)).thenReturn(Optional.of(user));
        when(userMapper.toDto(user)).thenReturn(userDTO);

        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn(username);

        UserDTO result = userService.updateUser(userDTO, authentication);

        assertEquals(userDTO, result);

        Mockito.verify(userRepository).findByUsernameIgnoreCase(username);
        Mockito.verify(userRepository).save(user);
    }
}