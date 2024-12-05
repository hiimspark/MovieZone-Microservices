package org.example.watchlistservice.service;

import lombok.AllArgsConstructor;
import org.example.watchlistservice.dto.MovieDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@AllArgsConstructor
public class MovieServiceClient {

    @Autowired
    private final RestTemplate restTemplate;

    public MovieDTO getMovieById(Long movieId) {
        return restTemplate.getForObject("http://movie-service:8082/movie/{movieId}", MovieDTO.class, movieId);
    }
}

