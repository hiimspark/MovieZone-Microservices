package org.example.movieservice.controller;

import lombok.AllArgsConstructor;
import org.example.movieservice.dto.MovieDTO;
import org.example.movieservice.entity.MovieEntity;
import org.example.movieservice.service.MovieService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
public class MovieController {
    private final MovieService movieService;

    @RequestMapping(value = "/movie", method = RequestMethod.GET)
    public ResponseEntity<List<MovieEntity>> readAll() {
        return new ResponseEntity<>(movieService.readAll(), HttpStatus.OK);
    }

    @RequestMapping(value = "/movie/{id}", method = RequestMethod.GET)
    public ResponseEntity<Optional<MovieEntity>> findByID(@PathVariable Long id) {
        return new ResponseEntity<>(movieService.findById(id), HttpStatus.OK);
    }

    @RequestMapping(value = "/movie", method = RequestMethod.POST)
    public ResponseEntity<MovieEntity> create(@RequestBody MovieDTO dto) {
        return new ResponseEntity<>(movieService.create(dto), HttpStatus.OK);
    }

    @RequestMapping(value = "/movie", method = RequestMethod.PUT)
    public ResponseEntity<MovieEntity> update(@RequestBody MovieEntity movie) {
        return new ResponseEntity<>(movieService.update(movie), HttpStatus.OK);
    }

    @DeleteMapping("/movie/{id}")
    public HttpStatus delete(@PathVariable Long id) {
        movieService.delete(id);
        return HttpStatus.OK;
    }
}