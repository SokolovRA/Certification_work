package ru.skypro.homework.dto;

import lombok.Data;
import ru.skypro.homework.enums.Role;

@Data
public class RegisterReq {
    private String userName;
    private String password;
    private String firstName;
    private String lastName;
    private String phone;
    private Role role;
}
