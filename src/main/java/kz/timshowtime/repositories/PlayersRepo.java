package kz.timshowtime.repositories;

import kz.timshowtime.models.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayersRepo extends JpaRepository<Player, Integer> {
    @Modifying
    @Query("UPDATE Player p SET p.currentScore = :currentScore WHERE p.id = :playerId")
    void updatePlayerCurrentScoreById(int playerId, Integer currentScore);
}
