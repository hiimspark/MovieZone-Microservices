package org.example.watchlistservice.controller;

import lombok.AllArgsConstructor;
import org.example.watchlistservice.dto.MovieDTO;
import org.example.watchlistservice.dto.UserDTO;
import org.example.watchlistservice.entity.WatchlistEntity;
import org.example.watchlistservice.service.WatchlistService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class WatchlistController {
    private final WatchlistService watchlistService;
    @RequestMapping(value = "/watchlist", method = RequestMethod.POST)
    public ResponseEntity<WatchlistEntity> create(@RequestParam("movie") Long movieId, @RequestHeader("X-UserName") String name) {
        return new ResponseEntity<>(watchlistService.addToList(name, movieId), HttpStatus.OK);

    }

}
