package org.example.watchlistservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.example.watchlistservice.entity.WatchlistEntity;

@Data
@AllArgsConstructor
public class WatchlistWithMovieDTO {
    private WatchlistEntity watchlistEntity;
    private MovieDTO movieDTO;
}