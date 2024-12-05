package org.example.watchlistservice.repository;

import org.example.watchlistservice.dto.UserDTO;
import org.example.watchlistservice.entity.WatchlistEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WatchlistRepository extends JpaRepository<WatchlistEntity, Long> {
    WatchlistEntity findByUserIdAndMovieId(Long userId, Long movieID);
    List<WatchlistEntity> findByUserId(Long userId);
}
