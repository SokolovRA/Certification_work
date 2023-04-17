package ru.skypro.homework.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.skypro.homework.enums.Role;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private String userName;
    private String password;
    private String addressDeals;
    @OneToOne
    private ImageAds imageAds;
    @Enumerated(EnumType.STRING)
    private Role role;
}
