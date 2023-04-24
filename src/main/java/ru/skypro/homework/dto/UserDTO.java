package ru.skypro.homework.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import ru.skypro.homework.model.User;

@Data
public class UserDTO {
        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        private Integer id;
        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        private String email;
        private String firstName;
        private String lastName;
        private String phone;
        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        private String image;

        public static UserDTO fromModel(User user) {
                UserDTO dto = new UserDTO();
                dto.setId(user.getId());
                dto.setEmail(user.getUsername());
                dto.setFirstName(user.getFirstName());
                dto.setLastName(user.getLastName());
                dto.setPhone(user.getPhone());
                if (user.getImage() == null) {
                        dto.setImage("");
                }
                else {
               dto.setImage("/users/me/image/" + user.getImage().getId());
                }
                return dto;
        }
}
