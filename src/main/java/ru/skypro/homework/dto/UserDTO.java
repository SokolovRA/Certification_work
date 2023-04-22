package ru.skypro.homework.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class UserDTO {
        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        private Integer id;
        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        private String username;
        private String firstName;
        private String lastName;
        private String phone;
        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        private String image;
}
