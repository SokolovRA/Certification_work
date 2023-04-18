package ru.skypro.homework.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreateAdsDTO {
    private String description;
    private Integer price;
    private String title;

}
