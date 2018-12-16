import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class BowlingScoreCalculatorTest {

    private static final List<Integer> ONLY_STRIKE = new ArrayList<>();
    private static final List<Integer> NO_STRIKE_NOR_SPARE = new ArrayList<>();

    static {
        for(int i = 0; i < 12; i++) {
            ONLY_STRIKE.add(10);
        }
        for(int i = 0; i < 20; i++) {
            NO_STRIKE_NOR_SPARE.add(4);
        }
    }

    @Test
    public void shouldReturnTheSumOfTheRollsScore() {
        List<Integer> rolls = new ArrayList<>();
        rolls.add(8);
        rolls.add(1);
        assertThat(9).isEqualTo(calculateScore(rolls));
    }

    @Test
    public void shouldReturn10forAStrike() {
        List<Integer> rolls = new ArrayList<>();
        rolls.add(10);
        assertThat(10).isEqualTo(calculateScore(rolls));
    }

    @Test
    public void shouldReturn30forADoubleStrike() {
        List<Integer> rolls = new ArrayList<>();
        rolls.add(10);
        rolls.add(10);
        assertThat(30).isEqualTo(calculateScore(rolls));
    }

    @Test
    public void shouldReturn60forATripleStrike() {
        List<Integer> rolls = new ArrayList<>();
        rolls.add(10);
        rolls.add(10);
        rolls.add(10);
        assertThat(60).isEqualTo(calculateScore(rolls));
    }

    @Test
    public void shouldReturn300for12Strikes() {
        List<Integer> rolls = new ArrayList<>();
        rolls.add(10);
        rolls.add(10);
        rolls.add(10);
        rolls.add(10);
        rolls.add(10);
        rolls.add(10);
        rolls.add(10);
        rolls.add(10);
        rolls.add(10);
        rolls.add(10);
        rolls.add(10);
        rolls.add(10);
        assertThat(300).isEqualTo(calculateScore(rolls));
    }

    @Test
    public void shouldReturn10forASpare() {
        List<Integer> rolls = new ArrayList<>();
        rolls.add(8);
        rolls.add(2);
        assertThat(10).isEqualTo(calculateScore(rolls));
    }

    @Test
    public void shouldReturn16forASpareAndA3() {
        List<Integer> rolls = new ArrayList<>();
        rolls.add(8);
        rolls.add(2);
        rolls.add(3);
        assertThat(16).isEqualTo(calculateScore(rolls));
    }

    private static int calculateScore(List<Integer> rolls) {
        int score = 0;
        int currentRoll = 0;

        for(Integer roll: rolls) {
            if(roll == 10) {
                int rollN1 = 0;
                if(rolls.size() > currentRoll + 1) {
                    rollN1 = rolls.get(currentRoll + 1);
                }
                int rollN2 = 0;
                if(rolls.size() > currentRoll + 2) {
                    rollN2 = rolls.get(currentRoll + 2);
                }
                score += rollN1 + rollN2;
            }
            else if(score == 10) {
                score += roll;
            }
            score += roll;
            currentRoll++;
            if(currentRoll == 10) {
                break;
            }
        }
        return score;
    }

    private List<Frame> getFrames(List<Integer> rolls) {
        List<Frame> frames = new ArrayList<>();
        Frame currentFrame = new Frame();
        boolean roll1 = true;
        int count = 0;
        for(Integer roll: rolls) {
            currentFrame.addRoll(roll);
            if(currentFrame.isAStrike()) {
                if(isTheLastFrame(count)) {
                    if(isTheLastRoll(count)) {
                        frames.add(currentFrame);
                    }
                } else {
                    frames.add(currentFrame);
                }
            } else {
                if(roll1) {
                    roll1 = false;
                } else {
                    roll1 = true;
                    frames.add(currentFrame);
                    currentFrame = new Frame();
                }

            }
            count++;
        }
        return frames;
    }

    private boolean isTheLastRoll(int count) {
        return count == 12;
    }

    private boolean isTheLastFrame(int count) {
        return count >= 10;
    }

    @Test
    public void shouldReturnAListFramesWith2FrameAnd4Roll() {
        List<Integer> rolls = new ArrayList<>();
        rolls.add(8);
        rolls.add(2);
        rolls.add(6);
        rolls.add(3);
        List<Frame> frames = getFrames(rolls);
        assertThat(frames.size()).isEqualTo(2);
        assertThat(frames.get(0).getFirstRoll()).isEqualTo(8);
        assertThat(frames.get(0).getSecondRoll()).isEqualTo(2);
        assertThat(frames.get(1).getFirstRoll()).isEqualTo(6);
        assertThat(frames.get(1).getSecondRoll()).isEqualTo(3);
    }

    @Test
    public void shouldReturn10FramesFor12Strikes() {
        assertThat(getFrames(ONLY_STRIKE).size()).isEqualTo(10);
    }

    @Test
    public void shouldReturn10FramesFor20Rolls() {
        assertThat(getFrames(NO_STRIKE_NOR_SPARE).size()).isEqualTo(10);
    }

    @Test
    public void shouldCalculateTheScoreOfAStrikeWithTheNext2Rolls() {
        Frame frame = new Frame();
        frame.addRoll(10);
        frame.nextRoll = 2;
        frame.nextNextRoll = 9;
        assertThat(frame.getScore()).isEqualTo(21);
    }

    static class Frame {
        List<Integer> rolls = new ArrayList<>(3);
        int nextRoll = 0;
        int nextNextRoll = 0;

        private boolean isAStrike() {
            return getFirstRoll() == 10;
        }

        int getFirstRoll() {
            return rolls.get(0);
        }

        int getSecondRoll() {
            return rolls.get(1);
        }

        int getBonusRoll() {
            return rolls.get(2);
        }

        void addRoll(int roll) {
            rolls.add(roll);
        }

        int getScore() {
            if(isAStrike()) {
                return getFirstRoll() + nextNextRoll + nextRoll;
            }
            return getFirstRoll() + getSecondRoll() + getBonusRoll();
        }
    }

}
