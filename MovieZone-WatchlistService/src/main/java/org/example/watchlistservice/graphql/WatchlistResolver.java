package org.example.watchlistservice.graphql;

import lombok.AllArgsConstructor;
import org.example.watchlistservice.entity.WatchlistEntity;
import org.example.watchlistservice.repository.WatchlistRepository;
import org.example.watchlistservice.service.WatchlistService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;
import java.util.Optional;

@Controller
@AllArgsConstructor
public class WatchlistResolver {
    private final WatchlistService watchlistService;
    private final WatchlistRepository watchlistRepository;

    @QueryMapping
    public List<WatchlistEntity> getUsersWatchlist(@Argument Long userId){
        return watchlistService.getUsersWatchlist(userId);
    }

    @MutationMapping
    public WatchlistEntity addMovieToWatchlist(@Argument Long movieId, @Argument String username){
        Long userId = watchlistService.getUserIdByName(username);
        return watchlistService.addToListByUserID(userId, movieId);
    }

    @MutationMapping
    public WatchlistEntity changeStatus (@Argument Long id, @Argument String status){
        Optional<WatchlistEntity> watchlistEntity = watchlistService.findWatchlistById(id);
        if (watchlistEntity.isEmpty()) {
            return null;
        }
        List<String> statuses = List.of("Watching", "Plan to watch", "Watched");
        if (!statuses.contains(status)){
            return null;
        }
        WatchlistEntity watchlist = watchlistEntity.get();
        watchlist.setWatchStatus(status);
        watchlistRepository.save(watchlist);
        return watchlist;
    }

    @MutationMapping
    public WatchlistEntity changeRating(@Argument Long id, @Argument Double rating){
        Optional<WatchlistEntity> watchlistEntity = watchlistService.findWatchlistById(id);
        if (watchlistEntity.isEmpty()) {
            return null;
        }
        if (rating > 10.0){
            return null;
        }
        WatchlistEntity watchlist = watchlistEntity.get();
        watchlist.setUserRating(rating);
        watchlistRepository.save(watchlist);
        return watchlist;
    }

    @MutationMapping
    public WatchlistEntity incrementEpisodes(@Argument Long id){
        Optional<WatchlistEntity> watchlistEntity = watchlistService.findWatchlistById(id);
        if (watchlistEntity.isEmpty()) {
            return null;
        }
        WatchlistEntity watchlist = watchlistEntity.get();
        if (watchlist.getWatchedEpisodes() < watchlist.getTotalEpisodes()) {
            watchlist.setWatchedEpisodes(watchlist.getWatchedEpisodes() + 1);
            watchlistRepository.save(watchlist);
            return watchlist;
        }
        else{
            return null;
        }
    }
}
