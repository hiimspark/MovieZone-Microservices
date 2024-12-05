package org.example.movieservice.controller;

import lombok.AllArgsConstructor;
import org.example.movieservice.entity.MovieEntity;
import org.example.movieservice.service.MovieService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

@Controller
@AllArgsConstructor
public class PagesController {
    private final MovieService movieService;

    @GetMapping("/library")
    public String menu(Model model, @RequestHeader("X-UserName") String name){
        List<MovieEntity> movies = movieService.readAll();
        model.addAttribute("name", name);
        model.addAttribute("movies", movies);
        return "library";
    }
}
