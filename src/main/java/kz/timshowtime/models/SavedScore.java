package kz.timshowtime.models;

import javax.persistence.*;

@Entity
@Table(name = "savedPlayerScores")
public class SavedScore {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "saveScore")
    private int saveScore;

    @ManyToOne
    @JoinColumn(name = "player_id", referencedColumnName = "id")
    private Player savedPlayer;

    @ManyToOne
    @JoinColumn(name = "game_id", referencedColumnName = "id")
    private Game game;

    public SavedScore(Player savedPlayer, Game game, int saveScore) {
        this.savedPlayer = savedPlayer;
        this.game = game;
        this.saveScore = saveScore;
    }

    public SavedScore() {}

    public Player getSavedPlayer() {
        return savedPlayer;
    }

    public void setSavedPlayer(Player savedPlayer) {
        this.savedPlayer = savedPlayer;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSaveScore() {
        return saveScore;
    }

    public void setSaveScore(int saveScore) {
        this.saveScore = saveScore;
    }

    public Player getPlayer() {
        return savedPlayer;
    }

    public void setPlayer(Player savedPlayer) {
        this.savedPlayer = savedPlayer;
    }
}
