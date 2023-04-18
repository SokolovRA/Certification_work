package ru.skypro.homework.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreateUserDTO {

        private String email;

        private String firstName;

        private String lastName;

        private String password;

        private String phone;
    }


