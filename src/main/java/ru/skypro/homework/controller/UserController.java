package ru.skypro.homework.controller;

import liquibase.repackaged.org.apache.commons.lang3.tuple.Pair;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.PasswordDTO;
import ru.skypro.homework.dto.UserDTO;
import ru.skypro.homework.service.ImageService;
import ru.skypro.homework.service.UserService;

@Slf4j
@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final ImageService imageService;

    @GetMapping("/me")
    public ResponseEntity<UserDTO> getAuthorizedUsers(Authentication authentication) {
        return ResponseEntity.ok(userService.getAuthorizedUsers(authentication));
    }
    @PostMapping("/set_password")
    public ResponseEntity<?> changeOldPassword(@RequestBody PasswordDTO newPassword,
                                               Authentication authentication) throws Exception {
        userService.updatePassword(newPassword, authentication);
        return ResponseEntity.ok().build();
    }
    @PatchMapping("/me")
    public ResponseEntity<UserDTO> updateUser(@RequestBody UserDTO userDto,
                                              Authentication authentication) throws Exception {
        return ResponseEntity.ok(userService.updateUser(userDto, authentication));
    }
    @PatchMapping(value = "/me/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateUserImage(@RequestPart("image") MultipartFile avatarFile,
                                             Authentication authentication) throws Exception {
        userService.updateUserImage(avatarFile, authentication);
        return ResponseEntity.ok().build();
    }
    @GetMapping(value = "/image/{id}", produces = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<byte[]> getImage(@PathVariable("id") Integer id) {
        Pair<String, byte[]> pair = imageService.getImage(id);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(pair.getLeft()))
                .contentLength(pair.getRight().length)
                .body(pair.getRight());
    }
}
