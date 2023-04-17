package ru.skypro.homework.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Ads {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private Long price;
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
    @OneToOne(fetch = FetchType.LAZY)
    private ImageAds imageAds;
}
