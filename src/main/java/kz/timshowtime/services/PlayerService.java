package kz.timshowtime.services;

import kz.timshowtime.models.Game;
import kz.timshowtime.models.Player;
import kz.timshowtime.models.SavedScore;
import kz.timshowtime.models.Score;
import kz.timshowtime.repositories.PlayersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@Transactional(readOnly = true)
public class PlayerService {
    private final PlayersRepo playersRepo;
    private final GameService gameService;

    @Autowired
    public PlayerService(PlayersRepo playersRepo, GameService gameService) {
        this.playersRepo = playersRepo;
        this.gameService = gameService;
    }

    public List<Player> findAll() {
        return playersRepo.findAll();
    }

    public Player findOne(int id) {
        return playersRepo.findById(id).orElse(null);
    }

    public Player initializeScoreList(Player player) {
        Player findPlayer = findOne(player.getId());
        findPlayer.getScoreList().size();
        return findPlayer;
    }

    @Transactional
    public void updateCurrentScore(int id, int currentScore) {
        playersRepo.updatePlayerCurrentScoreById(id, currentScore);
    }


    @Transactional // операции многий ко многим: игроку +1 игра, игре +каждый игрок
    public void setGames(List<Player> playerList, Game game) {
        Game bdGame = gameService.findOne(game.getId());
        for (Player player : playerList) {
            Player bdPlayer = findOne(player.getId());
            bdPlayer.addGame(bdGame);
            bdGame.addPlayer(bdPlayer);
        }
    }

    @Transactional // каждому игроку добавляем новый savedScore и то же самое к текущей игре
    public void commitScoreAndQuit(Player player, int savedScore, Game game) {
        Game bdGame = gameService.findOne(game.getId());
        Player bdPlayer = findOne(player.getId());
        SavedScore osc = new SavedScore(bdPlayer, bdGame, savedScore);
        bdPlayer.addSavedScore(osc);
        bdGame.addSavedScore(osc);
    }

    @Transactional
    public void setScore(List<Player> playerList) {
        playerList.forEach(p -> updateCurrentScore(p.getId(), p.getCurrentScore()));
    }

    @Transactional
    public void save(Player player) {
        playersRepo.save(player);
    }

    @Transactional
    public void update(int id, Player updatePlayer) {
        updatePlayer.setId(id);
        playersRepo.save(updatePlayer);
    }

    @Transactional
    public void setResults(List<Player> playerList) {
        for (Player player : playerList)
            findOne(player.getId()).addScore(new Score(player, player.getCurrentScore()));
        playerList = playerList.stream().map(p -> initializeScoreList(findOne(p.getId()))).toList();
        setMaxScorePlayer(playerList);
    }

    @Transactional
    public void setMaxScorePlayer(List<Player> playerList) {
        try {
            for (Player player : playerList) {
                updateCurrentScore(player.getId(), Collections.max(player.getScoreList(),
                        Comparator.comparingInt(Score::getValue)).getValue());
            }
        // если это новые игроки с пустым списком очков -> игнор, максимальное значение == текущему набранному
        } catch (NoSuchElementException ignored) {}
    }


    public void noNamePlayers(List<Player> playerList) {
        AtomicInteger count = new AtomicInteger(0);
        playerList.forEach(player -> {
            if (player.getName().equals(""))
                player.setName("Дурак_Который_Забыл_Ввести_Имя#" + count.incrementAndGet());
        });
    }

    @Transactional
    public void delete(int id) {
        playersRepo.deleteById(id);
    }
}
