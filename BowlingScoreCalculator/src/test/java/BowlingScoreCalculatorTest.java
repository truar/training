import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class BowlingScoreCalculatorTest {

    @Test
    public void shouldReturnTheRollScore() {
        assertThat(8).isEqualTo(calculateScore(8));
    }

    private int calculateScore(int roll) {
        return roll;
    }
}
