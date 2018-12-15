import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class BowlingScoreCalculatorTest {

    private static final Frame SPARE = new Frame(5, 5);
    private static final Frame REGULAR_8 = new Frame(5, 3);
    private static final Frame STRIKE = new Frame(10, 0);

    @Test
    public void shouldReturnTheSumForTheFrames() {
        List<Frame> frames = new LinkedList<>();
        frames.add(REGULAR_8);
        frames.add(REGULAR_8);
        assertThat(16).isEqualTo(calculateScore(frames));
    }

    @Test
    public void shouldReturn10ForASpare() {
        List<Frame> frames = new LinkedList<>();
        frames.add(SPARE);
        assertThat(10).isEqualTo(calculateScore(frames));
    }

    @Test
    public void shouldReturn23ForASpareAnd8() {
        List<Frame> frames = new LinkedList<>();
        frames.add(SPARE);
        frames.add(REGULAR_8);
        assertThat(23).isEqualTo(calculateScore(frames));
    }

    @Test
    public void shouldReturn18For8AndASpare() {
        List<Frame> frames = new LinkedList<>();
        frames.add(REGULAR_8);
        frames.add(SPARE);
        assertThat(18).isEqualTo(calculateScore(frames));
    }

    @Test
    public void shouldReturn10ForAStrike() {
        List<Frame> frames = new LinkedList<>();
        frames.add(STRIKE);
        assertThat(10).isEqualTo(calculateScore(frames));
    }

    @Test
    public void shouldReturn26ForAStrikeAnd8() {
        List<Frame> frames = new LinkedList<>();
        frames.add(STRIKE);
        frames.add(REGULAR_8);
        assertThat(26).isEqualTo(calculateScore(frames));
    }

    private int calculateScore(List<Frame> frames) {
        int score = 0;
        List<Frame> reversedFrames = reverseFrame(frames);
        Frame previousFrame = new Frame(0,0);
        int rollN1 = 0;
        int rollN2 = 0;
        for(Frame frame: reversedFrames) {
            int scoreCurrentFrame = frame.getScore();
            if(frame.isASpare()) {
                scoreCurrentFrame += rollN1;
            } else if(frame.isAStrike()) {
                scoreCurrentFrame += previousFrame.getScore();
            }
            score += scoreCurrentFrame;
            previousFrame = frame;
            rollN1 = frame.firstTry;
        }
        return score;
    }

    private List<Frame> reverseFrame(List<Frame> frames) {
        List<Frame> reversedFrames = new ArrayList<>(frames);
        Collections.reverse(reversedFrames);
        return reversedFrames;
    }

    private static class Frame {
        private final int firstTry;
        private final int secondTry;

        Frame(int firstTry, int secondTry) {
            this.firstTry = firstTry;
            this.secondTry = secondTry;
        }

        private int getScore() {
            return firstTry + secondTry;
        }

        private boolean isASpare() {
            return getScore() == 10 && firstTry < 10;
        }

        private boolean isAStrike() {
            return firstTry == 10;
        }
    }
}
