package com.zenika.zenikatas;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class BowlingScoreCalculatorTest {

    private static final List<Integer> ONLY_STRIKE = new ArrayList<>();
    private static final List<Integer> NO_STRIKE_NOR_SPARE = new ArrayList<>();
    private static final List<Integer> ONLY_SPARE = new ArrayList<>();
    private static final int MAX_FRAMES_PER_GAME = 10;

    static {
        for (int i = 0; i < 12; i++) {
            ONLY_STRIKE.add(10);
        }
        for (int i = 0; i < 10; i++) {
            NO_STRIKE_NOR_SPARE.add(4);
            NO_STRIKE_NOR_SPARE.add(5);
        }
        for (int i = 0; i < 10; i++) {
            ONLY_SPARE.add(5);
            ONLY_SPARE.add(5);
        }
        ONLY_SPARE.add(5);
    }

    @Test
    public void shouldReturn300GivenOnlyStrike() {
        assertThat(calculateScore(ONLY_STRIKE)).isEqualTo(300);
    }

    @Test
    public void shouldReturn150GivenOnlySpare() {
        assertThat(calculateScore(ONLY_SPARE)).isEqualTo(150);
    }

    @Test
    public void shouldReturn90GivenOnlySpare() {
        assertThat(calculateScore(NO_STRIKE_NOR_SPARE)).isEqualTo(90);
    }

    private static int calculateScore(List<Integer> onlyStrike) {
        List<Frame> frames = determineFramesFormRolls(onlyStrike);
        int score = 0;
        for (Frame f : frames) {
            score += f.getScore();
        }
        return score;
    }

    private static List<Frame> determineFramesFormRolls(List<Integer> rolls) {
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

    @Test
    public void shouldReturn10FramesFor12Strikes() {
        List<Frame> frames = determineFramesFormRolls(ONLY_STRIKE);
        assertThat(frames.size()).isEqualTo(10);
        assertThat(frames.get(0).nextRoll).isEqualTo(10);
        assertThat(frames.get(0).nextNextRoll).isEqualTo(10);
        assertThat(frames.get(9).nextRoll).isEqualTo(10);
        assertThat(frames.get(9).nextNextRoll).isEqualTo(10);
    }

    @Test
    public void shouldReturn10FramesFor12Spares() {
        List<Frame> frames = determineFramesFormRolls(ONLY_SPARE);
        assertThat(frames.size()).isEqualTo(10);
        assertThat(frames.get(0).nextRoll).isEqualTo(5);
        assertThat(frames.get(0).nextNextRoll).isEqualTo(0);
        assertThat(frames.get(9).nextRoll).isEqualTo(5);
        assertThat(frames.get(9).nextNextRoll).isEqualTo(0);
    }

    @Test
    public void shouldReturn10FramesFor20Rolls() {
        List<Frame> frames = determineFramesFormRolls(NO_STRIKE_NOR_SPARE);
        assertThat(frames.size()).isEqualTo(10);
        assertThat(frames.get(0).getFirstRoll()).isEqualTo(4);
        assertThat(frames.get(0).getSecondRoll()).isEqualTo(5);
        assertThat(frames.get(1).getFirstRoll()).isEqualTo(4);
        assertThat(frames.get(1).getSecondRoll()).isEqualTo(5);
    }

    @Test
    public void shouldCalculateTheScoreOfAStrikeWithTheNext2Rolls() {
        Frame frame = new Frame();
        frame.addRoll(10);
        frame.nextRoll = 2;
        frame.nextNextRoll = 9;
        assertThat(frame.getScore()).isEqualTo(21);
    }

    @Test
    public void shouldCalculateTheScoreOfASpareWithTheNextRoll() {
        Frame frame = new Frame();
        frame.addRoll(5);
        frame.addRoll(5);
        frame.nextRoll = 2;
        assertThat(frame.getScore()).isEqualTo(12);
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
