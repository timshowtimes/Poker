package kz.timshowtime.models;

import kz.timshowtime.enums.Mode;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Game {
    private List<Player> playerList;
    private int maxCardCount;
    private int extraRound;
    private int currCardCount = 1; // need to refresh
    private int modePos; // need to refresh
    private boolean decrease = false;
    private int dealerPos; // need to refresh
    private final String[] trumps = {"♥", "♦", "♠", "♣"};
    private Mode mode = Mode.DEFAULT; // need to refresh to Mode.DEFAULT

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
        return trumps[(int) (Math.random() * 4)];
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
}
