package ru.skypro.homework.interfaces;

import ru.skypro.homework.dto.RegisterDTO;
import ru.skypro.homework.enums.Role;

public interface UserAuthInterface {
    boolean login(String userName, String password);
    boolean register(RegisterDTO registerDTO, Role role);
}
