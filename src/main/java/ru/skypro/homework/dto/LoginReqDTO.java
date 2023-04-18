package ru.skypro.homework.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoginReqDTO {
    private String password;
    private String userName;

}
