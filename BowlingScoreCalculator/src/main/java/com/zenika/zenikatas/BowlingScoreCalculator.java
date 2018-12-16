package com.zenika.zenikatas;

import java.util.ArrayList;
import java.util.List;

public class BowlingScoreCalculator implements ScoreCalculator {

    public static final int MAX_FRAMES_PER_GAME = 10;

    public int calculateScore(List<Integer> rolls) {
        List<Frame> frames = determineFramesFromRolls(rolls);
        int score = 0;
        for (Frame f : frames) {
            score += f.getScore();
        }
        return score;
    }

    public static List<Frame> determineFramesFromRolls(List<Integer> rolls) {
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

}
