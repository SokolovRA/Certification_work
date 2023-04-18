package ru.skypro.homework.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Arrays;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="image")
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "file_size")
    private Long fileSize;
    @Column(name = "media_type")
    private String mediaType;
    @Lob
    @Type(type = "byte")
    @Column(name = "data_image")
    private byte[] data;
    public String toString() {
        return "Ads = " + this.getId() + ", image=" + Arrays.toString((this.getData())) ;
    }
}
