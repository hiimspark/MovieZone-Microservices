package org.example.movieservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Base64;
import java.util.Map;


@Entity
@Data
@Builder
@Table(name = "movies")
@NoArgsConstructor
@AllArgsConstructor
public class MovieEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;
    @Column
    private String name;
    @Column
    private String releaseDate;
    @Column
    private String description;
    @Column
    private double rating;
    @Column
    private boolean series;
    @Column
    @ElementCollection
    private Map<String, Integer> episodes;
    @Column
    private byte[] img;

    public String generateBase64Image(){
        return Base64.getEncoder().encodeToString(this.img);
    }

    @Transient
    private String episodeJson;

}