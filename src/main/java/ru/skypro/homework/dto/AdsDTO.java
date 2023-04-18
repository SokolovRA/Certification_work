package ru.skypro.homework.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdsDTO {
    private Long author;
    private String image;
    private Long pk;
    private Long price;
    private String title;
    @JsonIgnore
    private String description;

    }


