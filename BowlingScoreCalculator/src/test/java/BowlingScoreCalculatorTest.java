import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class BowlingScoreCalculatorTest {

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

    private int calculateScore(List<Integer> rolls) {
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
        Frame currentFrame = new Frame(0,0);
        boolean roll1 = true;
        boolean roll2 = false;
        int count = 0;
        for(Integer roll: rolls) {
            if(roll == 10) {
                if(count >= 10) {
                    if(roll1) {
                        currentFrame.firstRoll = roll;
                        roll1 = false;
                        roll2 = true;
                    } else if(roll2) {
                        currentFrame.secondRoll = roll;
                        roll2 = false;
                    } else {
                        currentFrame.bonusRoll = roll;
                        roll1 = true;
                        roll2 = false;
                        frames.add(currentFrame);
                    }
                } else {
                    frames.add(new Frame(roll, 0));
                }
            } else {
                if(roll1) {
                    currentFrame.firstRoll = roll;
                    roll1 = false;
                } else {
                    currentFrame.secondRoll = roll;
                    roll1 = true;
                    frames.add(currentFrame);
                    currentFrame = new Frame(0,0);
                }

            }
            count++;
        }
        return frames;
    }

    @Test
    public void shouldReturnAListFramesWithOneFrameAndOneRoll() {
        List<Integer> rolls = new ArrayList<>();
        rolls.add(10);
        assertThat(getFrames(rolls).size()).isEqualTo(1);
    }

    @Test
    public void shouldReturnAListFramesWith2FramesAndOneRoll() {
        List<Integer> rolls = new ArrayList<>();
        rolls.add(10);
        rolls.add(10);
        assertThat(getFrames(rolls).size()).isEqualTo(2);
    }

    @Test
    public void shouldReturnAListFramesWith1FrameAndTwoRoll() {
        List<Integer> rolls = new ArrayList<>();
        rolls.add(8);
        rolls.add(2);
        List<Frame> frames = getFrames(rolls);
        assertThat(frames.size()).isEqualTo(1);
        assertThat(frames.get(0).firstRoll).isEqualTo(8);
        assertThat(frames.get(0).secondRoll).isEqualTo(2);
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
        assertThat(frames.get(0).firstRoll).isEqualTo(8);
        assertThat(frames.get(0).secondRoll).isEqualTo(2);
        assertThat(frames.get(1).firstRoll).isEqualTo(6);
        assertThat(frames.get(1).secondRoll).isEqualTo(3);
    }

    @Test
    public void shouldReturn10FramesFor12Strikes() {
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
        assertThat(getFrames(rolls).size()).isEqualTo(10);
    }


    static class Frame {
        int firstRoll;
        int secondRoll;
        int bonusRoll;

        Frame(int firstRoll, int secondRoll) {
            this(firstRoll, secondRoll, 0);
        }


        Frame(int firstRoll, int secondRoll, int bonusRoll) {
            this.firstRoll = firstRoll;
            this.secondRoll = secondRoll;
            this.bonusRoll = bonusRoll;
        }
    }

}
