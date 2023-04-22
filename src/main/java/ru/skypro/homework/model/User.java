package ru.skypro.homework.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.skypro.homework.enums.Role;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @NotNull
    @NotEmpty
    @NotBlank
    @Column(name = "first_name")
    private String firstName;
    @NotNull
    @NotEmpty
    @NotBlank
    @Column(name = "last_name")
    private String lastName;
    @NotNull
    @NotEmpty
    @NotBlank
    @Column(name = "phone")
    private String phone;
    @NotNull
    @NotEmpty
    @NotBlank
    @Column(name = "username")
    private String username;
    @NotNull
    @NotEmpty
    @NotBlank
    @Column(name = "password")
    private String password;
    private Boolean enabled;

    @OneToOne
    @JoinColumn(name = "image_id")
    private Image image;
    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Role role;
    @OneToMany(mappedBy = "author")
    private List<Ads> ads;
    @OneToMany(mappedBy = "author")
    private List<Comment> comments;
}
