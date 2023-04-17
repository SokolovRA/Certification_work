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
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String createdTimeAds;
    private String content;
    @ManyToOne(fetch = FetchType.LAZY)
    private Ads ads;
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
}
