package com.zenika.zenikatas;

import java.util.ArrayList;
import java.util.List;

public class BowlingScoreCalculator {

    public static final int MAX_FRAMES_PER_GAME = 10;


    static int calculateScore(List<Integer> onlyStrike) {
        List<Frame> frames = determineFramesFormRolls(onlyStrike);
        int score = 0;
        for (Frame f : frames) {
            score += f.getScore();
        }
        return score;
    }

    static List<Frame> determineFramesFormRolls(List<Integer> rolls) {
        List<Frame> frames = new ArrayList<>();

        Frame currentFrame = new Frame();
        int currentRollIndex = -1;
        for(int i = 0; i < MAX_FRAMES_PER_GAME; i++) {
            currentRollIndex = getCurrentRollIndexAndAddToFrame(rolls, currentFrame, currentRollIndex);

            if (currentFrame.isAStrike()) {
                currentFrame.setNext2Rolls(rolls.get(currentRollIndex + 1), rolls.get(currentRollIndex + 2));
                currentFrame = addAndResetFrame(frames, currentFrame);
            } else {
                currentRollIndex = getCurrentRollIndexAndAddToFrame(rolls, currentFrame, currentRollIndex);

                if (currentFrame.isASpare()) {
                    currentFrame.setNextRoll(rolls.get(currentRollIndex + 1));
                }
                currentFrame = addAndResetFrame(frames, currentFrame);
            }
        }

        return frames;
    }

    private static int getCurrentRollIndexAndAddToFrame(List<Integer> rolls, Frame currentFrame, int currentRollIndex) {
        currentRollIndex++;
        currentFrame.addRoll(rolls.get(currentRollIndex));
        return currentRollIndex;
    }

    private static Frame addAndResetFrame(List<Frame> frames, Frame currentFrame) {
        frames.add(currentFrame);
        return new Frame();
    }

    static class Frame {
        List<Integer> rolls = new ArrayList<>(3);
        int nextRoll = 0;
        int nextNextRoll = 0;

        private boolean isAStrike() {
            return getFirstRoll() == 10;
        }

        private boolean isASpare() {
            return getFirstRoll() + getSecondRoll() == 10;
        }

        int getFirstRoll() {
            return rolls.get(0);
        }

        int getSecondRoll() {
            return (rolls.size() < 2) ? 0 : rolls.get(1);
        }

        void addRoll(int roll) {
            rolls.add(roll);
        }

        int getScore() {
            return getFirstRoll() + getSecondRoll() + nextRoll + nextNextRoll;
        }

        private void setNext2Rolls(Integer nextRoll, Integer nextNextRoll) {
            this.nextRoll = nextRoll;
            this.nextNextRoll = nextNextRoll;
        }

        private void setNextRoll(Integer nextRoll) {
            this.setNext2Rolls(nextRoll, 0);
        }
    }
}
