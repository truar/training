package com.zenika.zenikatas;

import java.util.ArrayList;
import java.util.List;

public class Frame {
    List<Integer> rolls = new ArrayList<>(3);
    public Integer nextRoll = 0;
    public Integer nextNextRoll = 0;

    public boolean isAStrike() {
        return getFirstRoll() == 10;
    }

    public boolean isASpare() {
        return getFirstRoll() + getSecondRoll() == 10;
    }

    public int getFirstRoll() {
        return rolls.get(0);
    }

    public int getSecondRoll() {
        return (rolls.size() < 2) ? 0 : rolls.get(1);
    }

    public void addRoll(int roll) {
        rolls.add(roll);
    }

    int getScore() {
        return getFirstRoll() + getSecondRoll() + nextRoll + nextNextRoll;
    }

    public void setNext2Rolls(Integer nextRoll, Integer nextNextRoll) {
        this.nextRoll = nextRoll;
        this.nextNextRoll = nextNextRoll;
    }

    public void setNextRoll(Integer nextRoll) {
        this.setNext2Rolls(nextRoll, 0);
    }
}
