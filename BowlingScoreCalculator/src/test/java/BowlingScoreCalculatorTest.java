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
    public void shouldReturn10forASpare() {
        List<Integer> rolls = new ArrayList<>();
        rolls.add(8);
        rolls.add(2);
        assertThat(10).isEqualTo(calculateScore(rolls));
    }

    private int calculateScore(List<Integer> rolls) {
        int score = 0;
        for(Integer roll: rolls) {
            score += roll;
        }
        return score;
    }
}
