package controller;

import model.Stone;
import org.junit.Before;
import model.GameBoard;
import org.junit.Test;

import static org.junit.Assert.*;

public class SimpleAITest {
    GameBoard model;
    SimpleAI sai;
    @Before
    public void setUp() throws Exception {
        model = new GameBoard();
        sai = new SimpleAI();

        model.setStone(0,0, Stone.X);
        model.setStone(0,1, Stone.X);
        model.setStone(2,0, Stone.X);
        model.setStone(2,2, Stone.X);

        model.setStone(0,2,Stone.O);
        model.setStone(1,1,Stone.O);
        model.setStone(2,1,Stone.O);
    }

    @Test
    public void stoneIsSetCorrectlyRowCheck() {
        sai.counter = 2;
        sai.updateGameBoard(model);

        assertEquals(Stone.O, model.getStone(1,0));
    }
}