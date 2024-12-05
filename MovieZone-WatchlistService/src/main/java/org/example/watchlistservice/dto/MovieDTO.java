package org.example.watchlistservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;

@Data
@AllArgsConstructor
public class MovieDTO {
    private Long id;
    private String name;
    private String releaseDate;
    private String description;
    private double  rating;
    private boolean series;
    private Map<String, Integer> episodes;
    private byte[] img;

    public void validateEpisodes() {
        if (!series) {
            this.episodes = Map.of("movie", 1);
        }
    }
}