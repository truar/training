import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class BowlingScoreCalculatorTest {

    @Test
    public void shouldReturnTheSumForTheFrame() {
        assertThat(7).isEqualTo(calculateFrame(5, 2));
        assertThat(9).isEqualTo(calculateFrame(4, 5));
    }

    private int calculateFrame(int firstTry, int secondTry) {
        return firstTry + secondTry;
    }
}
