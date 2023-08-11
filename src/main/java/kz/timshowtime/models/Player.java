package kz.timshowtime.models;

import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "player")
public class Player implements Comparable<Player>{
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name", length = 60, nullable = false)
    private String name;

    @Column(name = "currentScore")
    private int currentScore;

    @Column(name = "bet")
    private int bet;

    @Column(name = "takenFactScore")
    private int takenFactScore;

    @OneToMany(mappedBy = "player", cascade = CascadeType.ALL)
    private List<Score> scoreList;

    public void addScore(Score score) {
        scoreList.add(score);
    }

    public void refreshScore() {
        this.currentScore = 0;
    }

    public int getBet() {
        return bet;
    }

    public void setBet(int bet) {
        this.bet = bet;
    }

    public int getTakenFactScore() {
        return takenFactScore;
    }

    public void setTakenFactScore(int takenFactScore) {
        this.takenFactScore = takenFactScore;
    }

    public List<Score> getScoreList() {
        return scoreList;
    }

    public void setScoreList(List<Score> scoreList) {
        this.scoreList = scoreList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCurrentScore() {
        return currentScore;
    }

    public void summarizeCurrentScore(int score) {
        this.currentScore += score;
    }

    public void setCurrentScore(int currentScore) {
        this.currentScore = currentScore;
    }

    @Override
    public String toString() {
        return "Player{" +
                "score='" + currentScore + '\'' +
                '}';
    }

    @Override
    public int compareTo(Player o) {
        return o.currentScore - this.currentScore;
    }

}
