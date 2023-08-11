package kz.timshowtime.services;

import kz.timshowtime.enums.Mode;
import kz.timshowtime.models.Game;
import kz.timshowtime.models.Player;
import org.springframework.stereotype.Service;

@Service
public class GameService {
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

    public void setDefault(Game game, PlayerService playerService) {
        game.setMaxCardCount(36 / playerService.findAll().size());
        game.setPlayerList(playerService.findAll());
        game.getPlayerList().forEach(Player::refreshScore);
        game.setCurrCardCount(1);
        game.setModePos(0);
        game.setDealerPos(0);
        game.setDecrease(false);
        game.setExtraRound(0);
        game.setMode(Mode.DEFAULT);
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
