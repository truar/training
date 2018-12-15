import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class BowlingScoreCalculatorTest {

    @Test
    public void shouldReturnTheSumForTheFrames() {
        List<Frame> frames = new ArrayList<>();
        frames.add(new Frame(5, 2));
        frames.add(new Frame(4, 5));
        assertThat(16).isEqualTo(calculateScore(frames));
    }

    private int calculateScore(List<Frame> frames) {
        List<Frame> reversedFrames = new ArrayList<>(frames);
        Collections.reverse(reversedFrames);
        int score = 0;
        for(Frame frame: reversedFrames) {
            score += frame.firstTry + frame.secondTry;
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
