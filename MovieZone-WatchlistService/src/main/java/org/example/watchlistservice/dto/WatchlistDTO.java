package org.example.watchlistservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class WatchlistDTO {
    private Long userId;
    private Long movieId;
    private int totalEpisodes;
    private int watchedEpisodes;
    private double userRating;
    private String watchStatus;
}
