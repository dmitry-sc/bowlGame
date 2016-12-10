package aw.bowl.score.model;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by dmitry on 26.11.16.
 */

public class Frame {
    private Integer gameType = 10;
    private Boolean awaitsThrowAgain = false;
    private List<Integer> pins = new LinkedList<>();

    public Frame(Integer gameType) {
        this.gameType = gameType;
    }

    public Integer getScore() {
        return pins.stream().reduce(0, Integer::sum);
    }

    public boolean addRoll(Integer pins) {
        //cheater check
        if (getScore() + pins > gameType) {
            pins = gameType - getScore();
        }
        this.pins.add(pins);
        this.awaitsThrowAgain = !isStrike() && this.pins.size() < 2;
        return this.awaitsThrowAgain;
    }

    public boolean isSplit() {
        return (pins.size() == 2)
               &&
               (pins.get(0) + pins.get(1) == gameType);
    }

    public boolean isStrike() {
        return (pins.size() == 1)
               &&
               (pins.get(0).equals(gameType));
    }

    public Boolean isAwaitsThrowAgain() {
        return awaitsThrowAgain;
    }

    public List<Integer> getPins() {
        return pins;
    }
}
