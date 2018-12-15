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

    @Test
    public void shouldReturn10ForASpare() {
        List<Frame> frames = new ArrayList<>();
        frames.add(new Frame(5, 5));
        assertThat(10).isEqualTo(calculateScore(frames));
    }

    @Test
    public void shouldReturn23ForASpareAnd8() {
        List<Frame> frames = new ArrayList<>();
        frames.add(new Frame(5, 5));
        frames.add(new Frame(5, 3));
        assertThat(23).isEqualTo(calculateScore(frames));
    }

    private int calculateScore(List<Frame> frames) {
        List<Frame> reversedFrames = reverseFrame(frames);
        int score = 0;
        Frame previousFrame = null;
        for(Frame frame: reversedFrames) {
            int scoreCurrentFrame = frame.firstTry + frame.secondTry;
            if(scoreCurrentFrame == 10) {
                if(previousFrame != null) {
                    scoreCurrentFrame += previousFrame.firstTry;
                }
            }
            score += scoreCurrentFrame;
            previousFrame = frame;
        }
        return score;
    }

    private List<Frame> reverseFrame(List<Frame> frames) {
        List<Frame> reversedFrames = new ArrayList<>(frames);
        Collections.reverse(reversedFrames);
        return reversedFrames;
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
