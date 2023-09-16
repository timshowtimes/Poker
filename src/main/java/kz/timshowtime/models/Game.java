package kz.timshowtime.models;

import kz.timshowtime.enums.Mode;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "game")
public class Game {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "maxCardCount")
    private int maxCardCount;
    @Column(name = "extraRound")
    private int extraRound;
    @Column(name = "currCardCount")
    private int currCardCount = 1;
    @Column(name = "modePos")
    private int modePos;
    @Column(name = "decrease")
    private boolean decrease = false;
    @Column(name = "dealerPos")
    private int dealerPos;
    @Enumerated(EnumType.STRING)
    @Column(name = "mode")
    private Mode mode = Mode.DEFAULT;
    @ManyToMany(mappedBy = "games")
    private List<Player> playerList;

    @OneToMany(mappedBy = "game", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SavedScore> savedScoreList;

    public void addSavedScore(SavedScore savedScore) {
        savedScoreList.add(savedScore);
    }

    public List<SavedScore> getSavedScoreList() {
        return savedScoreList;
    }

    public void setSavedScoreList(List<SavedScore> savedScoreList) {
        this.savedScoreList = savedScoreList;
    }

    public void addPlayer(Player player) {
        playerList.add(player);
    }

    public void setDealerPos(int dealerPos) {
        this.dealerPos = dealerPos;
    }

    public void setMode(Mode mode) {
        this.mode = mode;
    }

    public void setExtraRound(int extraRound) {
        this.extraRound = extraRound;
    }

    public void setDecrease(boolean decrease) {
        this.decrease = decrease;
    }

    public void setCurrCardCount(int currCardCount) {
        this.currCardCount = currCardCount;
    }

    public void setModePos(int modePos) {
        this.modePos = modePos;
    }

    public int getExtraRound() {
        return extraRound;
    }

    public Player getDealer() {
        return playerList.get((dealerPos++) % playerList.size());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isDecrease() {
        return decrease;
    }

    public void incrementCardCount() {
        int val = this.currCardCount + 1;
        this.currCardCount = Math.min(val, maxCardCount);
    }

    public void extraRounds() {
        ++this.extraRound;
        if (this.extraRound == this.playerList.size() + 1) { // если прошли по всему кругу игроков
            this.extraRound = 0;
            this.currCardCount -= 1;
            this.decrease = true;
        }
    }

    public void decreaseCardCount() {
        int val = this.currCardCount - 1;
        if (val == 0) {
            this.decrease = false;
            this.currCardCount = this.maxCardCount;
            this.extraRound = 1;
            switchMode();
        } else
            this.currCardCount = val;
    }

    public void setPlayerList(List<Player> playerList) {
        this.playerList = playerList;
    }

    public String getRandTrump() {
        if ((int) (Math.random() * 37) == 7)
            return "JOKER";
        return new String[]{"♥", "♦", "♠", "♣"}[(int) (Math.random() * 4)];
    }

    public int getMaxCardCount() {
        return maxCardCount;
    }

    public void setMaxCardCount(int maxCardCount) {
        this.maxCardCount = maxCardCount;
    }

    public int getCurrCardCount() {
        return currCardCount;
    }

    public Mode getMode() {
        return mode;
    }

    public List<Player> getPlayerList() {
        return playerList;
    }

    public void switchMode() {
        mode = Mode.values()[++modePos % 5];
    }

    public int getModePos() {
        return modePos;
    }

    public int getDealerPos() {
        return dealerPos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Game game)) return false;
        return id == game.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        StringBuilder players = new StringBuilder();
        int i = 0;
        for (Player player : playerList) {
            if (i++ == 3)
                players.append("\n");
            players.append(player.getName()).append(", ");
        }
        int length = players.length();

        return players.delete(length-2, length).toString();
    }
}
