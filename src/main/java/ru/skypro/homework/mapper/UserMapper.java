package ru.skypro.homework.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import ru.skypro.homework.dto.UserDTO;
import ru.skypro.homework.model.Image;
import ru.skypro.homework.model.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "image", source = "image", qualifiedByName = "imageMapping")
    UserDTO toDto(User entity);

    @Named("imageMapping")
    default String imageMapping(Image image) {
        if (image == null) {
            return "";
        }
        return "/users/me/image/" + image.getId();
    }
}


