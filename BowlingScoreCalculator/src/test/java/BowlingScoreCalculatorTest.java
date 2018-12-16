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
}
