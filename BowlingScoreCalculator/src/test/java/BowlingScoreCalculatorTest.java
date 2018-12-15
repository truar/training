import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class BowlingScoreCalculatorTest {

    @Test
    public void shouldReturnTheSumForTheFrame() {
        assertThat(7).isEqualTo(calculateFrame(new Frame(5, 2)));
    }

    private int calculateFrame(Frame frame) {
        return frame.firstTry + frame.secondTry;
    }

    private class Frame {
        private final int firstTry;
        private final int secondTry;

        Frame(int firstTry, int secondTry) {
            this.firstTry = firstTry;
            this.secondTry = secondTry;
        }
    }
}
