import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class BowlingScoreCalculatorTest {

    private static final List<Integer> ONLY_STRIKE = new ArrayList<>();
    private static final List<Integer> NO_STRIKE_NOR_SPARE = new ArrayList<>();
    private static final List<Integer> ONLY_SPARE = new ArrayList<>();

    static {
        for(int i = 0; i < 12; i++) {
            ONLY_STRIKE.add(10);
        }
        for(int i = 0; i < 10; i++) {
            NO_STRIKE_NOR_SPARE.add(4);
            NO_STRIKE_NOR_SPARE.add(5);
        }
        for(int i = 0; i < 10; i++) {
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

    private int calculateScore(List<Integer> onlyStrike) {
        List<Frame> frames = determineFramesFormRolls(onlyStrike);
        int score = 0;
        for(Frame f : frames) {
            score += f.getScore();
        }
        return score;
    }

    private List<Frame> determineFramesFormRolls(List<Integer> rolls) {
        List<Frame> frames = new ArrayList<>();
        boolean roll1 = true;

        Frame currentFrame = new Frame();
        int currentFrameIndex = 0;
        int currentRollIndex = 0;
        while(currentFrameIndex < 10) {
            int roll = rolls.get(currentRollIndex);
            currentFrame.addRoll(roll);

            if(currentFrame.isAStrike()) {
                currentFrame.nextRoll = rolls.get(currentRollIndex + 1);
                currentFrame.nextNextRoll = rolls.get(currentRollIndex + 2);
                currentFrame = addAndResetFrame(frames, currentFrame);
                currentFrameIndex++;
            } else {
                if(roll1) {
                    roll1 = false;
                } else {
                    if(currentFrame.isASpare()) {
                        currentFrame.nextRoll = rolls.get(currentRollIndex + 1);
                    }
                    roll1 = true;
                    currentFrame = addAndResetFrame(frames, currentFrame);
                    currentFrameIndex++;
                }
            }
            currentRollIndex++;
        }


        return frames;
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
            return rolls.get(1);
        }

        void addRoll(int roll) {
            rolls.add(roll);
        }

        int getScore() {
            if(isAStrike()) {
                return getFirstRoll() + nextNextRoll + nextRoll;
            } else if (isASpare()) {
                return getFirstRoll() + getSecondRoll() + nextRoll;
            }
            return getFirstRoll() + getSecondRoll();
        }

    }

}
