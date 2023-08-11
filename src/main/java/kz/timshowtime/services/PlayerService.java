package kz.timshowtime.services;

import kz.timshowtime.models.Player;
import kz.timshowtime.models.Score;
import kz.timshowtime.repositories.PlayersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@Transactional(readOnly = true)
public class PlayerService {
    private final PlayersRepo playersRepo;

    @Autowired
    public PlayerService(PlayersRepo playersRepo) {
        this.playersRepo = playersRepo;
    }

    public List<Player> findAll() {
        return playersRepo.findAll();
    }

    public Player findOne(int id) {
        return playersRepo.findById(id).orElse(null);
    }

    public Player initializeScoreList(Player player) {
        Player findPlayer = findAll().stream()
                .filter(p -> p.getId() == player.getId())
                .findFirst().get();
        findPlayer.getScoreList().size();
        return findPlayer;
    }

    @Transactional
    public void updateCurrentScore(int id, Integer currentScore) {
        playersRepo.updatePlayerCurrentScoreById(id, currentScore);
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
    public void setResults() {
        findAll().forEach(p -> p.addScore(new Score(p, p.getCurrentScore())));
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
