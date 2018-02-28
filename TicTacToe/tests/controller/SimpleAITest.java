package controller;

import model.Stone;
import org.junit.Before;
import model.GameBoard;
import org.junit.Test;

import static org.junit.Assert.*;

public class SimpleAITest {
    private GameBoard model;
    private SimpleAI sai;

    @Before
    public void setUp() throws Exception {
        model = new GameBoard();
        sai = new SimpleAI();

        sai.counter = 2;
    }

    @Test
    public void stoneIsSetCorrectlyRowCheck() {
        setUpForRowCheck();

        sai.updateGameBoard(model);

        assertEquals(Stone.O, model.getStone(0, 2));
    }

    @Test
    public void stoneIsSetCorrectlyColCheck() {
        setUpForColCheck();

        sai.updateGameBoard(model);

        assertEquals(Stone.O, model.getStone(0, 2));
    }

    @Test
    public void stoneIsSetCorrectlyDiagonalCheck() {
        setUpForDiagonal();

        sai.updateGameBoard(model);

        assertEquals(Stone.O, model.getStone(2, 0));
    }

    @Test
    public void stoneIsSetCorrectlyStraightCheck() {
        setUpForStraight();

        sai.updateGameBoard(model);

        assertTrue(model.getStone(1,0) == Stone.O ||
                model.getStone(1,2) == Stone.O);
    }


    private void setUpForStraight() {
        model.setStone(0, 1, Stone.X);
        model.setStone(2, 1, Stone.X);

        model.setStone(1, 1, Stone.X);
    }

    private void setUpForRowCheck() {
        model.setStone(0, 0, Stone.X);
        model.setStone(0, 1, Stone.X);
        model.setStone(2, 0, Stone.X);
        model.setStone(2, 2, Stone.X);

        model.setStone(1, 1, Stone.O);
        model.setStone(2, 1, Stone.O);
    }

    private void setUpForColCheck() {
        model.setStone(2, 2, Stone.X);
        model.setStone(1, 2, Stone.X);

        model.setStone(1, 1, Stone.O);

    }

    private void setUpForDiagonal() {
        model.setStone(1, 1, Stone.X);
        model.setStone(0, 2, Stone.X);

        model.setStone(2, 2, Stone.O);
    }
}