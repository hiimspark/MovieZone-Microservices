package org.example.movieservice.service;

import lombok.AllArgsConstructor;
import org.example.movieservice.entity.MovieEntity;
import org.example.movieservice.dto.MovieDTO;
import org.example.movieservice.repository.MovieRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor
public class MovieService {
    private final MovieRepository movieRepository;

    public MovieEntity create(MovieDTO dto){
        Map<String, Integer> episodes;
        if (!dto.isSeries()) {
            episodes = Map.of("movie", 1);
        } else {
            episodes = dto.getEpisodes();
        }
        MovieEntity movie = MovieEntity.builder()
                .name(dto.getName())
                .releaseDate(dto.getReleaseDate())
                .description(dto.getDescription())
                .rating(dto.getRating())
                .series(dto.isSeries())
                .episodes(episodes)
                .img(dto.getImg())
                .build();
        return movieRepository.save(movie);
    }
    public List<MovieEntity> readAll() {
        return movieRepository.findAll();
    }

    public Optional<MovieEntity> findById(Long id){return movieRepository.findById(id);}

    public MovieEntity update(MovieEntity movie) {
        MovieEntity existingMovie = movieRepository.findById(movie.getId())
                .orElseThrow(() -> new IllegalArgumentException("Фильм с айди: " + movie.getId() + " - не найден"));

        existingMovie.setName(movie.getName());
        existingMovie.setReleaseDate(movie.getReleaseDate());
        existingMovie.setDescription(movie.getDescription());
        existingMovie.setRating(movie.getRating());
        existingMovie.setSeries(movie.isSeries());

        if (!movie.isSeries()) {
            existingMovie.setEpisodes(Map.of("movie", 1));
        } else {
            existingMovie.setEpisodes(movie.getEpisodes());
        }

        existingMovie.setImg(movie.getImg());

        return movieRepository.save(existingMovie);
    }


    public void delete(Long id) {
        movieRepository.deleteById(id);
    }
}