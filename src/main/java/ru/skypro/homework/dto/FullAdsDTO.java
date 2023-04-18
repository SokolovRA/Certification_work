package ru.skypro.homework.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FullAdsDTO {
    private String image;
    private String authorLastName;
    private String authorFirstName;
    private String phone;
    private Long price;
    private String description;
    private Long pk;
    private String title;
    private String email;

}


