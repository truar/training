package com.zenika.zenikatas;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import com.zenika.zenikatas.Frame;

import static org.assertj.core.api.Assertions.assertThat;

public class BowlingScoreCalculatorTest {

    private static final List<Integer> ONLY_STRIKE = new ArrayList<>();
    private static final List<Integer> NO_STRIKE_NOR_SPARE = new ArrayList<>();
    private static final List<Integer> ONLY_SPARE = new ArrayList<>();

    static {
        for (int i = 0; i < 12; i++) {
            ONLY_STRIKE.add(10);
        }
        for (int i = 0; i < 10; i++) {
            NO_STRIKE_NOR_SPARE.add(4);
            NO_STRIKE_NOR_SPARE.add(5);
        }
        for (int i = 0; i < 10; i++) {
            ONLY_SPARE.add(5);
            ONLY_SPARE.add(5);
        }
        ONLY_SPARE.add(5);
    }

    @Test
    public void shouldReturn300GivenOnlyStrike() {
        assertThat(BowlingScoreCalculator.calculateScore(ONLY_STRIKE)).isEqualTo(300);
    }

    @Test
    public void shouldReturn150GivenOnlySpare() {
        assertThat(BowlingScoreCalculator.calculateScore(ONLY_SPARE)).isEqualTo(150);
    }

    @Test
    public void shouldReturn90GivenOnlySpare() {
        assertThat(BowlingScoreCalculator.calculateScore(NO_STRIKE_NOR_SPARE)).isEqualTo(90);
    }

    @Test
    public void shouldReturn10FramesFor12Strikes() {
        List<Frame> frames = BowlingScoreCalculator.determineFramesFormRolls(ONLY_STRIKE);
        assertThat(frames.size()).isEqualTo(10);
        assertThat(frames.get(0).nextRoll).isEqualTo(10);
        assertThat(frames.get(0).nextNextRoll).isEqualTo(10);
        assertThat(frames.get(9).nextRoll).isEqualTo(10);
        assertThat(frames.get(9).nextNextRoll).isEqualTo(10);
    }

    @Test
    public void shouldReturn10FramesFor12Spares() {
        List<Frame> frames = BowlingScoreCalculator.determineFramesFormRolls(ONLY_SPARE);
        assertThat(frames.size()).isEqualTo(10);
        assertThat(frames.get(0).nextRoll).isEqualTo(5);
        assertThat(frames.get(0).nextNextRoll).isEqualTo(0);
        assertThat(frames.get(9).nextRoll).isEqualTo(5);
        assertThat(frames.get(9).nextNextRoll).isEqualTo(0);
    }

    @Test
    public void shouldReturn10FramesFor20Rolls() {
        List<Frame> frames = BowlingScoreCalculator.determineFramesFormRolls(NO_STRIKE_NOR_SPARE);
        assertThat(frames.size()).isEqualTo(10);
        assertThat(frames.get(0).getFirstRoll()).isEqualTo(4);
        assertThat(frames.get(0).getSecondRoll()).isEqualTo(5);
        assertThat(frames.get(1).getFirstRoll()).isEqualTo(4);
        assertThat(frames.get(1).getSecondRoll()).isEqualTo(5);
    }

    @Test
    public void shouldCalculateTheScoreOfAStrikeWithTheNext2Rolls() {
        Frame frame = new Frame();
        frame.addRoll(10);
        frame.nextRoll = 2;
        frame.nextNextRoll = 9;
        assertThat(frame.getScore()).isEqualTo(21);
    }

    @Test
    public void shouldCalculateTheScoreOfASpareWithTheNextRoll() {
        Frame frame = new Frame();
        frame.addRoll(5);
        frame.addRoll(5);
        frame.nextRoll = 2;
        assertThat(frame.getScore()).isEqualTo(12);
    }

}
