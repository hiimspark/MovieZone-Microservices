package org.example.watchlistservice.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

import org.example.watchlistservice.dto.MovieDTO;
import org.example.watchlistservice.dto.UserDTO;
import org.example.watchlistservice.entity.WatchlistEntity;
import org.example.watchlistservice.repository.WatchlistRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class WatchlistService {
    private final UserServiceClient userServiceClient;
    private final MovieServiceClient movieServiceClient;
    private final WatchlistRepository watchlistRepository;

    @Transactional
    public WatchlistEntity addToList (String name, Long movieId){
        Long userId = getUserIdByName(name);
        UserDTO user = userServiceClient.getUserById(userId);
        MovieDTO movie = movieServiceClient.getMovieById(movieId);
        System.out.println(movie.getDescription());
        if(!watchlistRepository.findAll().contains(findWatchlistItem(userId,movieId))) {
            int totalEpisodes = 0;
            for (Integer episodes : movie.getEpisodes().values()) {
                totalEpisodes += episodes;
            }
            WatchlistEntity watchlistMovie = WatchlistEntity.builder()
                    .userId(userId)
                    .movieId(movieId)
                    .totalEpisodes(totalEpisodes)
                    .watchedEpisodes(0)
                    .userRating(0.0)
                    .watchStatus("Watching")
                    .build();
            return watchlistRepository.save(watchlistMovie);
        }
        return null;
    }

    public WatchlistEntity addToListByUserID(Long userId, Long movieId){
        UserDTO user = userServiceClient.getUserById(userId);
        MovieDTO movie = movieServiceClient.getMovieById(movieId);
        System.out.println(movie.getDescription());
        if(!watchlistRepository.findAll().contains(findWatchlistItem(userId,movieId))) {
            int totalEpisodes = 0;
            for (Integer episodes : movie.getEpisodes().values()) {
                totalEpisodes += episodes;
            }
            WatchlistEntity watchlistMovie = WatchlistEntity.builder()
                    .userId(userId)
                    .movieId(movieId)
                    .totalEpisodes(totalEpisodes)
                    .watchedEpisodes(0)
                    .userRating(0.0)
                    .watchStatus("Watching")
                    .build();
            return watchlistRepository.save(watchlistMovie);
        }
        return null;
    }

    public WatchlistEntity findWatchlistItem(Long userId, Long movieId){
        return watchlistRepository.findByUserIdAndMovieId(userId, movieId);
    }

    public Optional<WatchlistEntity> findWatchlistById(Long id){
        return watchlistRepository.findById(id);
    }

    public Long getUserIdByName(String name){
        return userServiceClient.getUserByName(name).getId();
    }

    public List<WatchlistEntity> getUsersWatchlist(Long userId){
        List<WatchlistEntity> watchlist = watchlistRepository.findByUserId(userId);
        for (WatchlistEntity item : watchlist) {
            MovieDTO movie = movieServiceClient.getMovieById(item.getMovieId());
            item.setMovie(movie);

            UserDTO user = userServiceClient.getUserById(item.getUserId());
            item.setUser(user);
        }
        return watchlist;
    }
}
