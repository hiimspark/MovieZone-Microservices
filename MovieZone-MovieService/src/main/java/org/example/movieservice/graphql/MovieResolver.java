package org.example.movieservice.graphql;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.example.movieservice.dto.MovieDTO;
import org.example.movieservice.entity.MovieEntity;
import org.example.movieservice.service.MovieService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Map;

@Controller
@AllArgsConstructor
public class MovieResolver {
    private final MovieService movieService;

    @QueryMapping
    public List<MovieEntity> getAllMovies() { return movieService.readAll(); }

    @QueryMapping
    public MovieEntity getMovieById(@Argument Long id) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        MovieEntity movie = movieService.findById(id).orElse(null);
        String episodes = objectMapper.writeValueAsString(movie.getEpisodes());
        movie.setEpisodeJson(episodes);
        return movie;
    }

    @MutationMapping
    public MovieEntity createMovie(@Argument MovieDTO input){
        return movieService.create(input);
    }

    @MutationMapping
    public Boolean deleteMovie(@Argument Long id){
        movieService.delete(id);
        return true;
    }
}
