package controller;

import controller.GameController;
import model.GameBoard;
import model.Stone;


import java.util.concurrent.ThreadLocalRandom;

public class SimpleAI {
    GameBoard AIModel; //board for the AI

    public SimpleAI(GameBoard model) {
        this.AIModel = model;
    }

    //get the state of the gameboard
    public void updateGameBoard(GameBoard model) {
        addRandomStine(model);
    }

    //read the game board
    //read the board to know what fields are already taken,
    //but don't consider if it's a P1 stone or a AI stone
    //randomly add a stone on a free field
    private void addRandomStine(GameBoard model) {
        int row = getRandomNumber();
        int col = getRandomNumber();
        while (model.getStone(row, col) != Stone.NONE) {
            row = getRandomNumber();
            col = getRandomNumber();
        }
        model.setStone(row, col, Stone.O);
    }


    private int getRandomNumber() {
        int rnd = ThreadLocalRandom.current().nextInt(0, 3);
        return rnd;
    }
}
