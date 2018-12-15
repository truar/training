import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class BowlingScoreCalculatorTest {

    @Test
    public void shouldReturnTheSumForTheFrames() {
        List<Frame> frames = new ArrayList<>();
        frames.add(new Frame(5, 2));
        assertThat(7).isEqualTo(calculateScore(frames));
    }

    private int calculateScore(List<Frame> frames) {
        int score = 0;
        for(Frame frame: frames) {
            score = frame.firstTry + frame.secondTry;
        }
        return score;
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
