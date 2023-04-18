package ru.skypro.homework.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.skypro.homework.enums.Role;
@Data
@NoArgsConstructor
public class RegisterDTO {

        private String userName;

        private String password;

        private String firstName;

        private String lastName;

        private String phone;

        private Role role;
}
