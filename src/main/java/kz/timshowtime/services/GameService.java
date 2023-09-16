package kz.timshowtime.services;

import kz.timshowtime.enums.Mode;
import kz.timshowtime.models.Game;
import kz.timshowtime.models.Player;
import kz.timshowtime.repositories.GameRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class GameService {

    private final GameRepo gameRepo;

    public GameService(GameRepo gameRepo) {
        this.gameRepo = gameRepo;
    }

    public List<Game> findAll() {
        return gameRepo.findAll();
    }

    public Game findOne(int id) {
        return gameRepo.findById(id).orElse(null);
    }

    public List<Game> initPlayerList() {
        List<Game> gameList = findAll();
        gameList.forEach(g -> g.getPlayerList().size());
        return gameList;
    }

    public Game initSaveScore(int id) {
        Game game = findOne(id);
        game.getSavedScoreList().size();
        return game;
    }

    @Transactional
    public void save(Game game) {
        gameRepo.save(game);
    }

    @Transactional
    public void update(int id, Game updateGame) {
        updateGame.setId(id);
        gameRepo.save(updateGame);
    }

    @Transactional
    public void removePlayersGames(int id) {
        Game bdGame = findOne(id);
        for (Player player : bdGame.getPlayerList()) {
            List<Game> playerGames = player.getGames();
            Game gameForRemove = playerGames.stream()
                    .filter(g -> g.getId() == id).findAny().orElse(null);
            playerGames.remove(gameForRemove);
        }
    }

    @Transactional
    public void delete(int id) {
        gameRepo.deleteById(id);
    }


    public void calculateValues(Game singletonGame, Game formGame) {
        int i = 0;
        for (Player player : singletonGame.getPlayerList()) {
            Player formPlayer = formGame.getPlayerList().get(i++);
            int bet = formPlayer.getBet(), fact = formPlayer.getTakenFactScore();
            if (bet == 0)
                player.summarizeCurrentScore(5);
            else if (bet == fact)
                player.summarizeCurrentScore(bet * 10);
            else if (bet < fact)
                player.summarizeCurrentScore(fact);
            else
                player.summarizeCurrentScore(10 * (fact - bet));
        }
    }

    public void incrementOrDecreaseCardCount(Game game) {
        if (!game.isDecrease()) {
            game.incrementCardCount();
            if (game.getCurrCardCount() == game.getMaxCardCount()) {
                game.extraRounds();
            }
        } else
            game.decreaseCardCount();
    }

    public void makeProcess(Game singletonGame, Game formGame) {
        calculateValues(singletonGame, formGame);
        incrementOrDecreaseCardCount(singletonGame);
    }

    public void incrementExtraRoundForNotDefaultMode(Game game) {
        int val = game.getExtraRound() + 1;
        if (val == game.getPlayerList().size() + 1) {
            game.switchMode();
            game.setExtraRound(1);
        } else
            game.setExtraRound(val);

    }

    public void setDefault(Game game, List<Player> playerList) {
        game.setMaxCardCount(36 / playerList.size());
        game.setPlayerList(playerList);
        game.getPlayerList().forEach(Player::refreshScore);
    }

    public void misereCalculate(Game singletonGame, Game formGame) {
        int i = 0;
        for (Player player : singletonGame.getPlayerList()) {
            Player formPlayer = formGame.getPlayerList().get(i++);
            int fact = formPlayer.getTakenFactScore();
            if (fact > 0)
                player.summarizeCurrentScore(-10 * fact);
        }
    }

    public void goldCalculate(Game singletonGame, Game formGame) {
        int i = 0;
        for (Player player : singletonGame.getPlayerList()) {
            Player formPlayer = formGame.getPlayerList().get(i++);
            int fact = formPlayer.getTakenFactScore();
            if (fact > 0)
                player.summarizeCurrentScore(10 * fact);
        }
    }

}
