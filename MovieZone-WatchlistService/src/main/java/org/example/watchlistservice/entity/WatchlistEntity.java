package org.example.watchlistservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.watchlistservice.dto.MovieDTO;
import org.example.watchlistservice.dto.UserDTO;

import java.util.Base64;

@Entity
@Data
@Builder
@Table(name = "watchlist")
@NoArgsConstructor
@AllArgsConstructor
public class WatchlistEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private Long movieId;

    private int totalEpisodes;

    private int watchedEpisodes;

    private double userRating;

    private String watchStatus;

    @Transient
    private MovieDTO movie;

    @Transient
    private UserDTO user;

    public String generateBase64Image(){
        return Base64.getEncoder().encodeToString(this.movie.getImg());
    }


}
