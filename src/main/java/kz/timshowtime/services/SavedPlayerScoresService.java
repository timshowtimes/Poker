package kz.timshowtime.services;

import kz.timshowtime.models.Game;
import kz.timshowtime.models.Player;
import kz.timshowtime.models.SavedScore;
import kz.timshowtime.repositories.SavedPlayerScoresRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class SavedPlayerScoresService {
    private final SavedPlayerScoresRepo savedPlayerScoresRepo;
    private final GameService gameService;

    @Autowired
    public SavedPlayerScoresService(SavedPlayerScoresRepo savedPlayerScoresRepo, GameService gameService) {
        this.savedPlayerScoresRepo = savedPlayerScoresRepo;
        this.gameService = gameService;
    }

    @Transactional
    public void updateSaveScore(int saveScore, int playerId, int gameId) {
        savedPlayerScoresRepo.updateSavedPlayerScore(saveScore, playerId, gameId);
    }
}
