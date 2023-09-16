package kz.timshowtime.repositories;

import kz.timshowtime.models.SavedScore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SavedPlayerScoresRepo extends JpaRepository<SavedScore, Integer> {
    @Modifying
    @Query("UPDATE SavedScore ss SET ss.saveScore = :saveScore " +
            "WHERE ss.savedPlayer.id = :playerId and ss.game.id = :gameId")
    void updateSavedPlayerScore(int saveScore, int playerId, int gameId);
}
