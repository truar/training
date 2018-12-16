package com.zenika.zenikatas;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

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

    private BowlingScoreCalculator calculator;

    @Test
    public void shouldReturn300GivenOnlyStrike() {
        calculator = new BowlingScoreCalculator(ONLY_STRIKE);
        assertThat(calculator.calculateScore()).isEqualTo(300);
    }

    @Test
    public void shouldReturn150GivenOnlySpare() {
        calculator = new BowlingScoreCalculator(ONLY_SPARE);
        assertThat(calculator.calculateScore()).isEqualTo(150);
    }

    @Test
    public void shouldReturn90GivenOnlySpare() {
        calculator = new BowlingScoreCalculator(NO_STRIKE_NOR_SPARE);
        assertThat(calculator.calculateScore()).isEqualTo(90);
    }

    @Test
    public void shouldReturn10FramesFor12Strikes() {
        calculator = new BowlingScoreCalculator(ONLY_STRIKE);
        List<Frame> frames = calculator.determineFramesFromRolls();
        assertThat(frames.size()).isEqualTo(10);
        assertThat(frames.get(0).nextRoll).isEqualTo(10);
        assertThat(frames.get(0).nextNextRoll).isEqualTo(10);
        assertThat(frames.get(9).nextRoll).isEqualTo(10);
        assertThat(frames.get(9).nextNextRoll).isEqualTo(10);
    }

    @Test
    public void shouldReturn10FramesFor12Spares() {
        calculator = new BowlingScoreCalculator(ONLY_SPARE);
        List<Frame> frames = calculator.determineFramesFromRolls();
        assertThat(frames.size()).isEqualTo(10);
        assertThat(frames.get(0).nextRoll).isEqualTo(5);
        assertThat(frames.get(0).nextNextRoll).isEqualTo(0);
        assertThat(frames.get(9).nextRoll).isEqualTo(5);
        assertThat(frames.get(9).nextNextRoll).isEqualTo(0);
    }

    @Test
    public void shouldReturn10FramesFor20Rolls() {
        calculator = new BowlingScoreCalculator(NO_STRIKE_NOR_SPARE);
        List<Frame> frames = calculator.determineFramesFromRolls();
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
