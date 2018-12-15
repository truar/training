import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class BowlingScoreCalculatorTest {

    @Test
    public void shouldReturnTheSumForTheFrame() {
        assertThat(10).isEqualTo(calculateFrame(5, 5));
    }

    private int calculateFrame(int firstTry, int secondTry) {
        return firstTry + secondTry;
    }
}
