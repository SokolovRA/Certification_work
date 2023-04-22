package ru.skypro.homework.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.PasswordDTO;
import ru.skypro.homework.dto.UserDTO;
import ru.skypro.homework.mapper.UserMapper;
import ru.skypro.homework.model.User;
import ru.skypro.homework.repository.UserRepository;

@Transactional
@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final ImageService imageService;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;


    public UserDTO getAuthorizedUsers(Authentication authentication) {
        return userMapper.toDto(getUserByUsername(authentication.getName()));
    }

    public UserDTO updateUser(UserDTO userDto, Authentication authentication) throws Exception {
        User user = userRepository.findByUsernameIgnoreCase(authentication.getName()).orElseThrow(() -> new Exception("User not found"));
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setPhone(userDto.getPhone());
        userRepository.save(user);
        return userMapper.toDto(user);
    }

    public void updateUserImage(MultipartFile avatar,
                                  Authentication authentication) throws Exception {
        User user = userRepository.findByUsernameIgnoreCase(authentication.getName()).orElseThrow(() -> new Exception("User not found"));
        if (user.getImage() != null) {
            imageService.remove(user.getImage());
        }
        user.setImage(imageService.uploadImage(avatar));
        userRepository.save(user);
    }
    public void updatePassword(PasswordDTO newPassword, Authentication authentication) {
        User user = getUserByUsername(authentication.getName());
        if (!passwordEncoder.matches(newPassword.getCurrentPassword(), user.getPassword())) {
            throw new UsernameNotFoundException("User not found is email");
        }
        user.setPassword(passwordEncoder.encode(newPassword.getNewPassword()));
        userRepository.save(user);
    }
    public User getUserByUsername(String userName) {
        return userRepository.findByUsernameIgnoreCase(userName).orElseThrow(() ->
                new UsernameNotFoundException("User not found is email"));
    }
}
