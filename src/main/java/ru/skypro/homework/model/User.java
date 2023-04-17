package ru.skypro.homework.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;

    private String lastName;


    private String email;                               // User's email address


    private String password;


    private String phoneNumber;
}
