package kz.timshowtime.models;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.ArrayList;
import java.util.List;

public class PlayersCount {
    @Min(value = 3, message = "Минимальное количество игроков: 3")
    @Max(value = 10, message = "Максимальное количество игроков: 10")
    private int value = 3;

    private List<Player> playerList;

    public static void createPlayerList(int size, PlayersCount count) {
        List<Player> players = new ArrayList<>();
        for (int i = 0; i < size; i++)
            players.add(new Player());

        count.setPlayerList(players);
    }

    public List<Player> getPlayerList() {
        return playerList;
    }

    public void setPlayerList(List<Player> playerList) {
        this.playerList = playerList;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
