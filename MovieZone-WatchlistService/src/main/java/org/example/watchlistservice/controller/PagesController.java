package org.example.watchlistservice.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.example.watchlistservice.entity.WatchlistEntity;
import org.example.watchlistservice.repository.WatchlistRepository;
import org.example.watchlistservice.service.WatchlistService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@AllArgsConstructor
public class PagesController {
    private final WatchlistService watchlistService;
    private final WatchlistRepository watchlistRepository;

    @GetMapping("/watchlist")
    public String watchlistPage(Model model, HttpServletRequest request){
        String name = request.getHeader("X-UserName");
        Long id = watchlistService.getUserIdByName(name);
        List<WatchlistEntity> watchlist = watchlistService.getUsersWatchlist(id);
        Map<String, List<WatchlistEntity>> sortedByStatus = watchlist.stream()
                .collect(Collectors.groupingBy(WatchlistEntity::getWatchStatus));

        model.addAttribute("username", name);
        model.addAttribute("allStatuses", List.of("Watching", "Plan to watch", "Watched"));
        model.addAttribute("movies", sortedByStatus);
        return "watchlist";
    }

    @PostMapping("/watchlist/{id}/status")
    public ResponseEntity<String> updateWatchStatus(@PathVariable Long id, @RequestParam("status") String status) {
        Optional<WatchlistEntity> watchlistOptional = watchlistRepository.findById(id);

        if (watchlistOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        WatchlistEntity watchlist = watchlistOptional.get();
        watchlist.setWatchStatus(status);
        watchlistRepository.save(watchlist);

        return ResponseEntity.ok("Watch status updated successfully");
    }

    @PostMapping("/watchlist/{id}/rating")
    public ResponseEntity<String> updateUserRating(@PathVariable Long id, @RequestParam("rating") double rating) {
        Optional<WatchlistEntity> watchlistOptional = watchlistRepository.findById(id);

        if (watchlistOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        WatchlistEntity watchlist = watchlistOptional.get();
        watchlist.setUserRating(rating);
        watchlistRepository.save(watchlist);

        return ResponseEntity.ok("User rating updated successfully");
    }

    @PostMapping("/watchlist/{id}/increment")
    public ResponseEntity<String> incrementWatchedEpisodes(@PathVariable Long id) {
        Optional<WatchlistEntity> watchlistOptional = watchlistRepository.findById(id);

        if (watchlistOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        WatchlistEntity watchlist = watchlistOptional.get();
        if (watchlist.getWatchedEpisodes() < watchlist.getTotalEpisodes()) {
            watchlist.setWatchedEpisodes(watchlist.getWatchedEpisodes() + 1);
            watchlistRepository.save(watchlist);
            return ResponseEntity.ok("Watched episodes incremented successfully");
        }

        return ResponseEntity.badRequest().body("Cannot increment beyond total episodes");
    }
}
