package ru.skypro.homework.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import ru.skypro.homework.dto.CreateUserDTO;
import ru.skypro.homework.dto.RegisterDTO;
import ru.skypro.homework.dto.UserDTO;
import ru.skypro.homework.model.Image;
import ru.skypro.homework.model.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    CreateUserDTO toCreateUserDto(User entity);
    User createUserDtoToEntity(CreateUserDTO dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "email", source = "userName")
    @Mapping(target = "password", source = "password")
    @Mapping(target = "image", ignore = true)
    @Mapping(target = "role", defaultValue = "USER")
    User toEntity(RegisterDTO dto);
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "image", ignore = true)
    @Mapping(target = "role", ignore = true)
    User toEntity(UserDTO dto);
    @Mapping(target = "image", source = "image", qualifiedByName = "imageMapping")
    UserDTO toDto(User entity);

    @Named("imageMapping")
    default String imageMapping(Image image) {
        if (image == null) {
            return "";
        }
        return "/user/image/" + image.getId();
    }
}


